package com.zsb.blueprint.backend.core.runtime.nodes.impl;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import com.zsb.blueprint.backend.core.runtime.nodes.PureNode;
import com.zsb.blueprint.backend.core.runtime.params.ParamSource;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class FunctionCallPureNode extends PureNode {
    private final Method method;
    private final Map<String, ParamSource<?>> inputs = new HashMap<>();
    private final Map<String, ParamWrapper<?>> outputs = new HashMap<>();

    public FunctionCallPureNode(String id, String name, Method method) {
        super(id, name);
        this.method = method;
    }

    public void setParamInput(String name, ParamSource<?> source) {
        inputs.put(name, source);
    }

    public <T> void setParamOutput(String name, ParamWrapper<T> wrapper) {
        outputs.put(name, wrapper);
    }

    @Override
    public Object evaluate(String outputName, ExecutionContext ctx) {
        try {
            ExecutionContext.prepareArgs(ctx, inputs, outputs, method);
            return outputs.get(outputName).value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
