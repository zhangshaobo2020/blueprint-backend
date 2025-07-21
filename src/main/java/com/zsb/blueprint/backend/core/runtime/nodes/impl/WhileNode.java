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
    private String loopBodyExec;
    private String completedExec;
    private boolean breakFlag = false;

    public static String BREAK = "Break";

    public WhileNode(String id, String name) {
        super(id, name);
    }

    public void doBreak() {
        this.breakFlag = true;
        this.execute(getCtx());
    }

    @Override
    public String execute(ExecutionContext ctx) {
        // 检查 Break 信号
        if (ctx.isBreakRequested(getId())) {
            ctx.clearBreak(getId());
            return completedExec;
        }
        if (breakFlag) {
            breakFlag = false; // 重置
            return completedExec;
        }
        if (condition != null && Boolean.TRUE.equals(condition.getValue(ctx))) {
            return loopBodyExec != null ? loopBodyExec : getId();
        } else {
            return completedExec;
        }
    }
}
