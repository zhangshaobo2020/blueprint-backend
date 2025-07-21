package com.zsb.blueprint.backend.core.runtime.nodes;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class PureNode extends BaseNode {
    public PureNode(String id, String name) {
        super(id, name);
    }

    public abstract Object evaluate(String outputName, ExecutionContext ctx);
}
