package com.zsb.blueprint.backend.web.controller;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import com.zsb.blueprint.backend.core.runtime.nodes.impl.*;
import com.zsb.blueprint.backend.web.dto.GraphDTO;
import com.zsb.blueprint.backend.web.dto.NodeDTO;
import com.zsb.blueprint.backend.web.unify.WebResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/blueprint/compile")
public class BlueprintCompileController {

    @PostMapping("/convert")
    public WebResult<String> receiveNodes(@RequestBody GraphDTO graphDTO) throws Exception {

        ExecutionContext ctx = new ExecutionContext();
        Map<String, String> nodeInputsMapping = new HashMap<>();
        Map<String, String> nodeOutputsMapping = new HashMap<>();

        for (NodeDTO node : graphDTO.getNodes()) {
            boolean hasExec = node.isHasExec();
            if (hasExec) {
                switch (node.getMeta().getQualifiedName()) {
                    case "CONTROL.BeginPlay": {
                        BeginPlayNode beginPlay = new BeginPlayNode(node.getId(), node.getMeta().getQualifiedName());
                        node.getOutputs().forEach((outputId, output) -> {
                            nodeOutputsMapping.put(node.getId(), outputId);
                        });
                        ctx.addExecNode(beginPlay);
                        break;
                    }
                    case "CONTROL.EndPlay": {
                        EndPlayNode endPlayNode = new EndPlayNode(node.getId(), node.getMeta().getQualifiedName());
                        node.getInputs().forEach((inputId, input) -> {
                            nodeInputsMapping.put(node.getId(), inputId);
                        });
                        ctx.addExecNode(endPlayNode);
                        break;
                    }
                    case "CONTROL.Branch": {
                        BranchNode branchNode = new BranchNode(node.getId(), node.getMeta().getQualifiedName());
                        node.getInputs().forEach((inputId, input) -> {
                            nodeInputsMapping.put(node.getId(), inputId);
                        });
                        node.getOutputs().forEach((outputId, output) -> {
                            nodeOutputsMapping.put(node.getId(), outputId);
                        });
                        ctx.addExecNode(branchNode);
                        break;
                    }
                    case "CONTROL.While": {
                        WhileNode whileNode = new WhileNode(node.getId(), node.getMeta().getQualifiedName());
                        node.getInputs().forEach((inputId, input) -> {
                            nodeInputsMapping.put(node.getId(), inputId);
                        });
                        node.getOutputs().forEach((outputId, output) -> {
                            nodeOutputsMapping.put(node.getId(), outputId);
                        });
                        ctx.addExecNode(whileNode);
                        break;
                    }
                    case "CONTROL.ForLoop": {
                        ForLoopNode forLoopNode = new ForLoopNode(node.getId(), node.getMeta().getQualifiedName());
                        node.getInputs().forEach((inputId, input) -> {
                            nodeInputsMapping.put(node.getId(), inputId);
                        });
                        node.getOutputs().forEach((outputId, output) -> {
                            nodeOutputsMapping.put(node.getId(), outputId);
                        });
                        ctx.addExecNode(forLoopNode);
                        break;
                    }
                    case "CONTROL.Break": {
                        BreakExecNode breakExecNode = new BreakExecNode(node.getId(), node.getMeta().getQualifiedName());
                        node.getInputs().forEach((inputId, input) -> {
                            nodeInputsMapping.put(node.getId(), inputId);
                        });
                        node.getOutputs().forEach((outputId, output) -> {
                            nodeOutputsMapping.put(node.getId(), outputId);
                        });
                        ctx.addExecNode(breakExecNode);
                        break;
                    }
                    default: {
                        // 说明是FunctionCallExecNode
                        Method method = Class.forName("com.zsb.blueprint.backend.defaults.functions." + node.getMeta().getCategory())
                                .getMethod(node.getMeta().getName());
                        FunctionCallExecNode functionCallExecNode = new FunctionCallExecNode(node.getId(), node.getMeta().getQualifiedName(), method);
                    }
                }
            } else {
                // 说明是FunctionCallPureNode
                Method method = Class.forName("com.zsb.blueprint.backend.defaults.functions." + node.getMeta().getCategory())
                        .getMethod(node.getMeta().getName());
                FunctionCallPureNode functionCallPureNode = new FunctionCallPureNode(node.getId(), node.getMeta().getQualifiedName(), method);
            }
        }


        // 处理节点数据
        return WebResult.success("Received");
    }
}
