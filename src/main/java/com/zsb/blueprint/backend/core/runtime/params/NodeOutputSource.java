package com.zsb.blueprint.backend.core.runtime.params;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NodeOutputSource<T> implements ParamSource<T> {
    private final String nodeId;
    private final String outputName;

    @Override
    public T getValue(ExecutionContext ctx) {
        return ctx.getNodeOutput(nodeId, outputName);
    }
}
