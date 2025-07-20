package com.zsb.blueprint.backend.core.runtime.params;

import com.zsb.blueprint.backend.core.runtime.nodes.impl.FunctionCallNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class NodeOutputSource<T> implements ParamSource<T> {
    private final FunctionCallNode node;
    private final String outputName;

    public T getValue() {
        return (T) node.getOutput(outputName).value;
    }
}
