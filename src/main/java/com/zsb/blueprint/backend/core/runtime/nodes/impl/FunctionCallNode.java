package com.zsb.blueprint.backend.core.runtime.nodes.impl;

import com.zsb.blueprint.backend.core.annotations.ParamInput;
import com.zsb.blueprint.backend.core.annotations.ParamOutput;
import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import com.zsb.blueprint.backend.core.runtime.nodes.ExecNode;
import com.zsb.blueprint.backend.core.runtime.params.ParamSource;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
public class FunctionCallNode extends ExecNode {
    private final Method method;
    private final Map<String, ParamSource<?>> inputSources = new HashMap<>();
    private final Map<String, ParamWrapper<?>> outputTargets = new HashMap<>();
    private String nextExec;

    public FunctionCallNode(String id, Method method) {
        super(id);
        this.method = method;
    }

    public void addInput(String name, ParamSource<?> source) {
        inputSources.put(name, source);
    }

    public void addOutput(String name, ParamWrapper<?> target) {
        outputTargets.put(name, target);
    }

    public ParamWrapper<?> getOutput(String name) {
        return outputTargets.get(name);
    }

    @Override
    public String execute(ExecutionContext ctx) {
        try {
            // 准备参数
            Parameter[] params = method.getParameters();
            Object[] args = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                ParamInput in = params[i].getAnnotation(ParamInput.class);
                ParamOutput out = params[i].getAnnotation(ParamOutput.class);
                if (in != null) {
                    ParamWrapper<?> wrapper = new ParamWrapper<>(inputSources.get(in.value()).getValue());
                    args[i] = wrapper;
                } else if (out != null) {
                    ParamWrapper<?> wrapper = outputTargets.get(out.value());
                    args[i] = wrapper;
                }
            }
            method.invoke(null, args); // 默认只访问 public static 方法
        } catch (Exception e) {
            log.error("FunctionCallNode execute error", e);
        }
        return nextExec;
    }
}
