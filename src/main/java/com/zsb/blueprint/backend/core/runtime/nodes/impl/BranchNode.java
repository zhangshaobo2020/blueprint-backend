package com.zsb.blueprint.backend.core.runtime.nodes.impl;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import com.zsb.blueprint.backend.core.runtime.nodes.ExecNode;
import com.zsb.blueprint.backend.core.runtime.params.ParamSource;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BranchNode extends ExecNode {
    private ParamSource<Boolean> condition;
    private String trueExec;
    private String falseExec;

    public BranchNode(String id, String name) {
        super(id, name);
    }

    @Override
    public String execute(ExecutionContext ctx) {
        boolean cond = condition.getValue(ctx);
        return cond ? trueExec : falseExec;
    }
}
