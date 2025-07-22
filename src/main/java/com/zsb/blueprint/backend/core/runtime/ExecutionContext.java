package com.zsb.blueprint.backend.core.runtime;

import com.zsb.blueprint.backend.core.annotations.ParamInput;
import com.zsb.blueprint.backend.core.annotations.ParamOutput;
import com.zsb.blueprint.backend.core.runtime.nodes.ExecNode;
import com.zsb.blueprint.backend.core.runtime.nodes.PureNode;
import com.zsb.blueprint.backend.core.runtime.params.ParamSource;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@SuppressWarnings("unchecked")
public class ExecutionContext {

    private final Map<String, ExecNode> execNodes = new HashMap<>();
    private final Map<String, PureNode> pureNodes = new HashMap<>();

    private final Set<String> breakRequests = new HashSet<>();

    public void addExecNode(ExecNode node) {
        execNodes.put(node.getId(), node);
        node.setCtx(this);
    }

    public void addPureNode(PureNode node) {
        pureNodes.put(node.getId(), node);
        node.setCtx(this);
    }

    public <T> T getNodeOutput(String nodeId, String outputName) {
        PureNode pure = pureNodes.get(nodeId);
        if (pure != null) {
            return (T) pure.evaluate(outputName, this);
        }
        // 为了ForLoop控制节点的Index输出
        ExecNode execNode = execNodes.get(nodeId);
        if (execNode != null) {
            return (T) execNode.getOutputParam(outputName).value;
        }
        return null;
    }

    public void run(String startId) {
        String current = startId;
        while (current != null) {
            ExecNode node = execNodes.get(current);
//            System.out.println("ExecNode当前节点是：" + node.getId());
            current = node.execute(this);
        }
    }

    public static void prepareArgs(ExecutionContext ctx, Map<String, ParamSource<?>> inputs, Map<String, ParamWrapper<?>> outputs, Method method) throws IllegalAccessException, InvocationTargetException {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[inputs.size() + outputs.size()];
        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            ParamInput paramInput = param.getAnnotation(ParamInput.class);
            ParamOutput paramOutput = param.getAnnotation(ParamOutput.class);
            String paramName;
            if (paramInput != null) {
                paramName = paramInput.value();
            } else if (paramOutput != null) {
                paramName = paramOutput.value();
            } else {
                // 如果没有ParamInput，尝试用参数类型推断
                paramName = param.getName(); // 仅作为 fallback
            }

            if (inputs.containsKey(paramName)) {
                args[i] = new ParamWrapper<>(inputs.get(paramName).getValue(ctx));
            } else if (outputs.containsKey(paramName)) {
                args[i] = outputs.get(paramName);
            } else {
                throw new RuntimeException(
                        "No binding found for parameter: " + paramName +
                                " in method: " + method.getName()
                );
            }
        }
        method.invoke(null, args);
    }

    // ============= Break 支持 =============
    public void notifyBreak(String loopNodeId) {
        breakRequests.add(loopNodeId);
    }

    public boolean isBreakRequested(String loopNodeId) {
        return breakRequests.contains(loopNodeId);
    }

    public void clearBreak(String loopNodeId) {
        breakRequests.remove(loopNodeId);
    }
}
