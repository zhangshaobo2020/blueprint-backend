package com.zsb.blueprint.backend.web.controller;

import com.zsb.blueprint.backend.core.config.CustomPathConfig;
import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import com.zsb.blueprint.backend.core.runtime.nodes.impl.*;
import com.zsb.blueprint.backend.core.runtime.params.LiteralValueSource;
import com.zsb.blueprint.backend.core.runtime.params.NodeOutputSource;
import com.zsb.blueprint.backend.core.runtime.params.ParamSource;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;
import com.zsb.blueprint.backend.web.dto.ConnectionDTO;
import com.zsb.blueprint.backend.web.dto.GraphDTO;
import com.zsb.blueprint.backend.web.dto.IOEntryDTO;
import com.zsb.blueprint.backend.web.dto.NodeDTO;
import com.zsb.blueprint.backend.web.unify.WebResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blueprint/compile")
public class BlueprintCompileController {

    @Resource
    private CustomPathConfig customPathConfig;

    @PostMapping("/test")
    public WebResult<String> receiveNodes(@RequestBody GraphDTO graphDTO) throws Exception {
        List<NodeDTO> nodes = graphDTO.getNodes();
        List<ConnectionDTO> connections = graphDTO.getConnections();
        ExecutionContext ctx = new ExecutionContext();

        for (NodeDTO node : nodes) {
            boolean hasExec = node.isHasExec();
            if (hasExec) {
                switch (node.getMeta().getQualifiedName()) {
                    case "CONTROL.BeginPlay": {
                        BeginPlayNode beginPlay = new BeginPlayNode(node.getId(), node.getMeta().getQualifiedName());
                        beginPlay.setNextExec(findForNextExecNode(node, "Exec", connections));
                        ctx.addExecNode(beginPlay);
                        break;
                    }
                    case "CONTROL.EndPlay": {
                        EndPlayNode endPlayNode = new EndPlayNode(node.getId(), node.getMeta().getQualifiedName());
                        ctx.addExecNode(endPlayNode);
                        break;
                    }
                    case "CONTROL.Branch": {
                        BranchNode branchNode = new BranchNode(node.getId(), node.getMeta().getQualifiedName());
                        // 查找Cond引脚是否有进行连接
                        String nodePinMapping = findForPreviousParamPin(node, "Cond", nodes, connections);
                        if (StringUtils.isBlank(nodePinMapping)) {
                            branchNode.setCondition(
                                    new LiteralValueSource<>(
                                            (Boolean) findForCurrentPinValue(node, "Cond", Boolean.class)
                                    )
                            );
                        } else {
                            String[] split = nodePinMapping.split("-");
                            branchNode.setCondition(new NodeOutputSource<>(split[0], split[1]));
                        }
                        branchNode.setTrueExec(findForNextExecNode(node, "True", connections));
                        branchNode.setFalseExec(findForNextExecNode(node, "False", connections));

                        if (StringUtils.isBlank(branchNode.getTrueExec())) {
                            // 如果自己后面没有跟Exec，要递归自己的父循环代码块
                            String mayExistsPreviousLoopingNode = findForPreviousLoopingNode(node, nodes, connections);
                            if (StringUtils.isNotBlank(mayExistsPreviousLoopingNode)) {
                                branchNode.setTrueExec(mayExistsPreviousLoopingNode);
                            }
                        }
                        if (StringUtils.isBlank(branchNode.getFalseExec())) {
                            // 如果自己后面没有跟Exec，要递归自己的父循环代码块
                            String mayExistsPreviousLoopingNode = findForPreviousLoopingNode(node, nodes, connections);
                            if (StringUtils.isNotBlank(mayExistsPreviousLoopingNode)) {
                                branchNode.setFalseExec(mayExistsPreviousLoopingNode);
                            }
                        }
                        ctx.addExecNode(branchNode);
                        break;
                    }
                    case "CONTROL.While": {
                        WhileNode whileNode = new WhileNode(node.getId(), node.getMeta().getQualifiedName());
                        // 查找Cond引脚是否有进行连接
                        String nodePinMapping = findForPreviousParamPin(node, "Cond", nodes, connections);
                        if (StringUtils.isBlank(nodePinMapping)) {
                            whileNode.setCondition(
                                    new LiteralValueSource<>(
                                            (Boolean) findForCurrentPinValue(node, "Cond", Boolean.class)
                                    )
                            );
                        } else {
                            String[] split = nodePinMapping.split("-");
                            whileNode.setCondition(new NodeOutputSource<>(split[0], split[1]));
                        }
                        whileNode.setLoopBodyExec(findForNextExecNode(node, "LoopBody", connections));
                        whileNode.setCompletedExec(findForNextExecNode(node, "Completed", connections));
                        ctx.addExecNode(whileNode);
                        break;
                    }
                    case "CONTROL.ForLoop": {
                        ForLoopNode forLoopNode = new ForLoopNode(node.getId(), node.getMeta().getQualifiedName());
                        ParamSource<Integer> fromPin;
                        // 查找From引脚是否有进行连接
                        String fromMapping = findForPreviousParamPin(node, "From", nodes, connections);
                        if (StringUtils.isBlank(fromMapping)) {
                            fromPin = new LiteralValueSource<>(
                                    (Integer) findForCurrentPinValue(node, "From", Integer.class)
                            );
                        } else {
                            String[] split = fromMapping.split("-");
                            fromPin = new NodeOutputSource<>(split[0], split[1]);
                        }
                        ParamSource<Integer> toPin;
                        // 查找To引脚是否有进行连接
                        String toMapping = findForPreviousParamPin(node, "To", nodes, connections);
                        if (StringUtils.isBlank(toMapping)) {
                            toPin = new LiteralValueSource<>(
                                    (Integer) findForCurrentPinValue(node, "To", Integer.class)
                            );
                        } else {
                            String[] split = toMapping.split("-");
                            toPin = new NodeOutputSource<>(split[0], split[1]);
                        }
                        forLoopNode.setRange(fromPin, toPin);
                        forLoopNode.setStepExec(findForNextExecNode(node, "Step", connections));
                        forLoopNode.setCompletedExec(findForNextExecNode(node, "Completed", connections));
                        ctx.addExecNode(forLoopNode);
                        break;
                    }
                    case "CONTROL.Break": {
                        BreakExecNode breakExecNode = new BreakExecNode(node.getId(), node.getMeta().getQualifiedName());
                        breakExecNode.setLoopTarget(findForPreviousLoopingNode(node, nodes, connections));
                        ctx.addExecNode(breakExecNode);
                        break;
                    }
                    default: {
                        // 说明是FunctionCallExecNode
                        Method method = getMethod(node.getMeta().getQualifiedName());
                        FunctionCallExecNode execNode = new FunctionCallExecNode(node.getId(), node.getMeta().getQualifiedName(), method);
                        dealWithFunctionCallExecNode(execNode, node, nodes, connections);

                        // 尝试查找下一个Exec节点
                        String nextExecNode = findForNextExecNode(node, "Exec", connections);
                        if (StringUtils.isNotBlank(nextExecNode)) {
                            execNode.setNextExec(nextExecNode);
                        } else {
                            // 如果自己后面没有跟Exec，要递归自己的父循环代码块
                            String mayExistsPreviousLoopingNode = findForPreviousLoopingNode(node, nodes, connections);
                            if (StringUtils.isNotBlank(mayExistsPreviousLoopingNode)) {
                                execNode.setNextExec(mayExistsPreviousLoopingNode);
                            }
                        }
                        ctx.addExecNode(execNode);
                        break;
                    }
                }
            } else {
                // 说明是FunctionCallPureNode
                Method method = getMethod(node.getMeta().getQualifiedName());
                FunctionCallPureNode pureNode = new FunctionCallPureNode(node.getId(), node.getMeta().getQualifiedName(), method);
                dealWithFunctionCallPureNode(pureNode, node, nodes, connections);
                ctx.addPureNode(pureNode);
            }
        }
        String beginPlayNodeId = getBeginPlayNodeId(nodes);
        if (StringUtils.isBlank(beginPlayNodeId)) throw new IllegalArgumentException("缺失BeginPlay节点");
        ctx.run(beginPlayNodeId);
        // 处理节点数据
        return WebResult.success("Received");
    }

    // 向后找执行引脚
    private String findForNextExecNode(NodeDTO node, String requiredPinName, List<ConnectionDTO> connections) {
        String nodeId = node.getId();
        String execOutputId = null;
        for (IOEntryDTO ioEntryDTO : node.getOutputs().values()) {
            // Exec引脚没有meta信息，从label中进行匹配
            if (ioEntryDTO.getSocket().getName().equals("SocketExec") &&
                    ioEntryDTO.getLabel().equals(requiredPinName)) {
                execOutputId = ioEntryDTO.getId();
            }
        }
        if (StringUtils.isBlank(execOutputId)) return null;
        for (ConnectionDTO connection : connections) {
            if (connection.getSource().equals(nodeId) && connection.getSourceOutput().equals(execOutputId)) {
                return connection.getTarget();
            }
        }
        return null;
    }

    // 向前找参数引脚
    private String findForPreviousParamPin(NodeDTO node, String requiredPinName,
                                           List<NodeDTO> nodes, List<ConnectionDTO> connections) {
        String nodeId = node.getId();
        String paramInputId = findParamInputId(node, requiredPinName);
        if (StringUtils.isBlank(paramInputId)) return null;
        for (ConnectionDTO connection : connections) {
            if (connection.getTarget().equals(nodeId) && connection.getTargetInput().equals(paramInputId)) {
                String sourceNodeId = connection.getSource();
                String sourceNodeOutputId = connection.getSourceOutput();
                // 要根据nodeId和nodeOutputId找到对应的参数名
                NodeDTO nodeDTO = getNodeDTO(nodes, sourceNodeId);
                assert nodeDTO != null;
                for (IOEntryDTO ioEntryDTO : nodeDTO.getOutputs().values()) {
                    if (ioEntryDTO.getId().equals(sourceNodeOutputId)) {
                        return connection.getSource() + "-" + ioEntryDTO.getMeta().getName();
                    }
                }
            }
        }
        return null;
    }

    // 向前找执行引脚
    private String findForPreviousLoopingNode(NodeDTO node, List<NodeDTO> nodes,
                                              List<ConnectionDTO> connections) {
        String nodeId = node.getId();
        String currentExecInPin = null;
        for (IOEntryDTO ioEntryDTO : node.getInputs().values()) {
            if (ioEntryDTO.getSocket().getName().equals("SocketExec")) {
                currentExecInPin = ioEntryDTO.getId();
            }
        }
        if (StringUtils.isBlank(currentExecInPin)) return null;
        for (ConnectionDTO connection : connections) {
            if (connection.getTarget().equals(nodeId) && connection.getTargetInput().equals(currentExecInPin)) {
                NodeDTO nodeDTO = getNodeDTO(nodes, connection.getSource());
                if (nodeDTO == null) return null;
                String nodeQualifiedName = nodeDTO.getMeta().getQualifiedName();
                String sourceOutputId = connection.getSourceOutput();
                if ("CONTROL.While".equals(nodeQualifiedName)) {
                    for (Map.Entry<String, IOEntryDTO> dtoEntry : nodeDTO.getOutputs().entrySet()) {
                        if (dtoEntry.getKey().equals(sourceOutputId)) {
                            if (dtoEntry.getValue().getSocket().getName().equals("SocketExec") &&
                                    dtoEntry.getValue().getMeta().getName().equals("LoopBody")) {
                                return nodeDTO.getId();
                            }
                        }
                    }
                } else if ("CONTROL.ForLoop".equals(nodeQualifiedName)) {
                    for (Map.Entry<String, IOEntryDTO> dtoEntry : nodeDTO.getOutputs().entrySet()) {
                        if (dtoEntry.getKey().equals(sourceOutputId)) {
                            if (dtoEntry.getValue().getSocket().getName().equals("SocketExec") &&
                                    dtoEntry.getValue().getMeta().getName().equals("Step")) {
                                return nodeDTO.getId();
                            }
                        }
                    }
                }
                return findForPreviousLoopingNode(nodeDTO, nodes, connections);
            }
        }
        return null;
    }

    // 从中找ForLoopNode节点的Index对应Id
    private String findForForLoopNodeIndexId(NodeDTO node) {
        for (IOEntryDTO ioEntryDTO : node.getOutputs().values()) {
            if (ForLoopNode.INDEX.equals(ioEntryDTO.getMeta().getName())) {
                return ioEntryDTO.getId();
            }
        }
        return null;
    }

    // 从中找参数默认值
    private Object findForCurrentPinValue(NodeDTO node, String requiredPinName, Class<?> requiredClass) {
        String paramInputId = findParamInputId(node, requiredPinName);
        if (StringUtils.isBlank(paramInputId)) return null;
        if (requiredClass.equals(Boolean.class)) {
            String strValue = node.getInputs().get(paramInputId).getControl().getValue().toString();
            return Boolean.parseBoolean(strValue);
        }
        return node.getInputs().get(paramInputId).getControl().getValue();
    }

    private String findParamInputId(NodeDTO node, String requiredPinName) {
        String paramInputId = null;
        for (IOEntryDTO ioEntryDTO : node.getInputs().values()) {
            if (ioEntryDTO.getSocket().getName().equals("SocketParam") &&
                    ioEntryDTO.getMeta().getName().equals(requiredPinName)) {
                paramInputId = ioEntryDTO.getId();
            }
        }
        return paramInputId;
    }

    private NodeDTO getNodeDTO(List<NodeDTO> nodes, String nodeId) {
        for (NodeDTO node : nodes) {
            if (node.getId().equals(nodeId)) return node;
        }
        return null;
    }

    private String getBeginPlayNodeId(List<NodeDTO> nodes) {
        for (NodeDTO node : nodes) {
            if (node.getMeta().getQualifiedName().equals("CONTROL.BeginPlay")) return node.getId();
        }
        return null;
    }

    private ConnectionDTO getConnectionDTO(List<ConnectionDTO> connections, String connectionId) {
        for (ConnectionDTO connection : connections) {
            if (connection.getId().equals(connectionId)) return connection;
        }
        return null;
    }

    private Method getMethod(String functionQualifiedName) throws Exception {
        String[] split = functionQualifiedName.split("\\.");
        String className = customPathConfig.blueprintFunctionPath + "." + split[0];
        String methodName = split[1];
        Class<?> clazz = Class.forName(className);
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new NoSuchMethodException(className + "." + methodName + "的同名方法不存在!");
    }

    private void dealWithFunctionCallExecNode(FunctionCallExecNode execNode, NodeDTO node,
                                              List<NodeDTO> nodes, List<ConnectionDTO> connections) throws Exception {
        for (Map.Entry<String, IOEntryDTO> entry : node.getInputs().entrySet()) {
            IOEntryDTO input = entry.getValue();
            boolean isParam = input.getSocket().getName().equals("SocketParam");
            if (isParam) {
                String paramName = input.getMeta().getName();
                Class<?> clazzType = Class.forName(input.getMeta().getType().getQualifiedName());

                ParamSource<?> paramPin;
                // 查找参数引脚是否有进行连接
                String paramMapping = findForPreviousParamPin(node, paramName, nodes, connections);
                if (StringUtils.isBlank(paramMapping)) {
                    paramPin = new LiteralValueSource<>(findForCurrentPinValue(node, paramName, clazzType));
                } else {
                    String[] split = paramMapping.split("-");
                    paramPin = new NodeOutputSource<>(split[0], split[1]);
                }
                execNode.setParamInput(paramName, paramPin);
            }
        }

        for (Map.Entry<String, IOEntryDTO> entry : node.getOutputs().entrySet()) {
            IOEntryDTO output = entry.getValue();
            boolean isParam = output.getSocket().getName().equals("SocketParam");
            if (isParam) {
                String paramName = output.getMeta().getName();
                execNode.setParamOutput(paramName, new ParamWrapper<>());
            }
        }
    }

    private void dealWithFunctionCallPureNode(FunctionCallPureNode pureNode, NodeDTO node,
                                              List<NodeDTO> nodes, List<ConnectionDTO> connections) throws Exception {
        for (Map.Entry<String, IOEntryDTO> entry : node.getInputs().entrySet()) {
            IOEntryDTO input = entry.getValue();
            boolean isParam = input.getSocket().getName().equals("SocketParam");
            if (isParam) {
                String paramName = input.getMeta().getName();
                Class<?> clazzType = Class.forName(input.getMeta().getType().getQualifiedName());

                ParamSource<?> paramPin;
                // 查找参数引脚是否有进行连接
                String paramMapping = findForPreviousParamPin(node, paramName, nodes, connections);
                if (StringUtils.isBlank(paramMapping)) {
                    paramPin = new LiteralValueSource<>(findForCurrentPinValue(node, paramName, clazzType));
                } else {
                    String[] split = paramMapping.split("-");
                    paramPin = new NodeOutputSource<>(split[0], split[1]);
                }
                pureNode.setParamInput(paramName, paramPin);
            }
        }

        for (Map.Entry<String, IOEntryDTO> entry : node.getOutputs().entrySet()) {
            IOEntryDTO output = entry.getValue();
            boolean isParam = output.getSocket().getName().equals("SocketParam");
            if (isParam) {
                String paramName = output.getMeta().getName();
                pureNode.setParamOutput(paramName, new ParamWrapper<>());
            }
        }
    }
}
