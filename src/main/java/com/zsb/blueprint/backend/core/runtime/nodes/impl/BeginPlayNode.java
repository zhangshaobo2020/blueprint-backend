package com.zsb.blueprint.backend.core.runtime.nodes.impl;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import com.zsb.blueprint.backend.core.runtime.nodes.ExecNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BeginPlayNode extends ExecNode {

    private String nextExec;

    public BeginPlayNode(String id, String name) {
        super(id, name);
    }

    public void setNextExec(String nextExec) {
        this.nextExec = nextExec;
    }

    @Override
    public String execute(ExecutionContext ctx) {
        // BeginPlay 仅作为起点，直接进入下一节点
        return nextExec;
    }
}
