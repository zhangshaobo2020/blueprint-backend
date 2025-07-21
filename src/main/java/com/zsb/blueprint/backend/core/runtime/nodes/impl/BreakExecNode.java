package com.zsb.blueprint.backend.core.runtime.nodes.impl;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import com.zsb.blueprint.backend.core.runtime.nodes.ExecNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BreakExecNode extends ExecNode {
    private String loopTarget; // 要break的循环节点ID

    public BreakExecNode(String id, String name) {
        super(id, name);
    }

    @Override
    public String execute(ExecutionContext ctx) {
        ctx.notifyBreak(loopTarget);
        // Break节点执行后不进入nextExec，因为跳出了循环
        return loopTarget;
    }
}
