package com.zsb.blueprint.backend.core.runtime.nodes.impl;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import com.zsb.blueprint.backend.core.runtime.nodes.ExecNode;
import com.zsb.blueprint.backend.core.runtime.params.ParamSource;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ForLoopNode extends ExecNode {

    private ParamSource<Integer> from;
    private ParamSource<Integer> to;
    private String stepExec;
    private String completedExec;
    private int currentIndex;
    private boolean started = false;
    private boolean breakFlag = false;
    // 缓存值
    private int cachedIndex;

    public static String INDEX = "Index";
    public static String BREAK = "Break";

    public ForLoopNode(String id, String name) {
        super(id, name);
    }

    public void setRange(ParamSource<Integer> from, ParamSource<Integer> to) {
        this.from = from;
        this.to = to;
        // 赋值from作为起始索引
        cachedIndex = from.getValue(getCtx());
    }

    @Override
    public ParamWrapper<?> getOutputParam(String outputName) {
        if ("Index".equals(outputName)) {
            return new ParamWrapper<>(cachedIndex);
        } else {
            return super.getOutputParam(outputName);
        }
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
        if (!started) {
            currentIndex = from.getValue(ctx);
            started = true;
        }
        if (breakFlag) {
            breakFlag = false;
            return completedExec;
        }
        if (currentIndex < to.getValue(ctx)) {
            // 暂存当前值
            cachedIndex = currentIndex;
            currentIndex++;
            return stepExec != null ? stepExec : getId();
        } else {
            started = false;
            return completedExec;
        }
    }
}
