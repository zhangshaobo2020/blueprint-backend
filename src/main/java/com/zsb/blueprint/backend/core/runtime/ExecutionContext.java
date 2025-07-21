package com.zsb.blueprint.backend.core.runtime;

import com.zsb.blueprint.backend.core.runtime.nodes.ExecNode;
import com.zsb.blueprint.backend.core.runtime.nodes.PureNode;
import com.zsb.blueprint.backend.core.runtime.params.ParamSource;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Data
@SuppressWarnings("unchecked")
public class ExecutionContext {

    private final Map<String, ExecNode> execNodes = new HashMap<>();
    private final Map<String, PureNode> pureNodes = new HashMap<>();

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
            System.out.println("ExecNode当前节点是：" + node.getId());
            current = node.execute(this);
        }
    }

    public static void prepareArgs(ExecutionContext ctx, Map<String, ParamSource<?>> inputs, Map<String, ParamWrapper<?>> outputs, Method method) throws IllegalAccessException, InvocationTargetException {
        Object[] args = new Object[inputs.size() + outputs.size()];
        int i = 0;
        for (ParamSource<?> src : inputs.values()) {
            args[i++] = new ParamWrapper<>(src.getValue(ctx));
        }
        for (ParamWrapper<?> out : outputs.values()) {
            args[i++] = out;
        }
        method.invoke(null, args);
    }
}
