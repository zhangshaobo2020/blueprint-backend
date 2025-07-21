package com.zsb.blueprint.backend.core.runtime.nodes.impl;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import com.zsb.blueprint.backend.core.runtime.nodes.ExecNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EndPlayNode extends ExecNode {

    public EndPlayNode(String id, String name) {
        super(id, name);
    }

    @Override
    public String execute(ExecutionContext ctx) {
        // EndPlay 是终点，不再有下一步
        return null;
    }
}
