package com.zsb.blueprint.backend.core.runtime.nodes.impl;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import com.zsb.blueprint.backend.core.runtime.nodes.ExecNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BreakNode extends ExecNode {
    private final WhileNode targetLoop;
    private String nextExec;

    public BreakNode(String id, WhileNode targetLoop) {
        super(id);
        this.targetLoop = targetLoop;
    }

    @Override
    public String execute(ExecutionContext ctx) {
        ctx.setBreak();
        return nextExec; // 跳出循环
    }
}
