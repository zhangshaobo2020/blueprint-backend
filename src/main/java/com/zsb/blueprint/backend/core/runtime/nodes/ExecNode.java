package com.zsb.blueprint.backend.core.runtime.nodes;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public abstract class ExecNode extends BaseNode {

    protected final Map<String, ParamWrapper<?>> outputs = new HashMap<>();

    public ExecNode(String id, String name) {
        super(id, name);
    }

    public ParamWrapper<?> getOutputParam(String outputName) {
        return outputs.get(outputName);
    }

    public abstract String execute(ExecutionContext ctx);
}
