package com.zsb.blueprint.backend.core.runtime.nodes.impl;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import com.zsb.blueprint.backend.core.runtime.nodes.ExecNode;
import com.zsb.blueprint.backend.core.runtime.params.ParamSource;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WhileNode extends ExecNode {
    private ParamSource<Boolean> condition;
    private String bodyExec;
    private String exitExec;

    public WhileNode(String id) {
        super(id);
    }

    @Override
    public String execute(ExecutionContext ctx) {
        if (ctx.isBreak()) {
            ctx.setBreak(); // 防止残留
            return exitExec;
        }
        if (condition.getValue()) {
            return bodyExec;
        } else {
            return exitExec;
        }
    }
}
