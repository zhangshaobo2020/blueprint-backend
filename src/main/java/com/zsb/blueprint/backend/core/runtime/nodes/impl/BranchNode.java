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

    public BranchNode(String id) {
        super(id);
    }

    public void setCondition(ParamSource<Boolean> condition) {
        this.condition = condition;
    }

    public void setTrueExec(String trueExec) {
        this.trueExec = trueExec;
    }

    public void setFalseExec(String falseExec) {
        this.falseExec = falseExec;
    }

    @Override
    public String execute(ExecutionContext ctx) {
        return condition.getValue() ? trueExec : falseExec;
    }
}
