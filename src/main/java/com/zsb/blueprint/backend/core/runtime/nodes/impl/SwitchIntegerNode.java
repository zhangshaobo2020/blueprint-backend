package com.zsb.blueprint.backend.core.runtime.nodes.impl;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import com.zsb.blueprint.backend.core.runtime.nodes.ExecNode;
import com.zsb.blueprint.backend.core.runtime.params.ParamSource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class SwitchIntegerNode extends ExecNode {
    private ParamSource<Integer> cond;
    private final Map<Integer, String> caseExecs = new HashMap<>();
    private String defaultExec;

    public SwitchIntegerNode(String id, String name) {
        super(id, name);
    }

    public void setCondition(ParamSource<Integer> cond) {
        this.cond = cond;
    }

    public void addCase(int value, String execNodeId) {
        caseExecs.put(value, execNodeId);
    }

    @Override
    public String execute(ExecutionContext ctx) {
        if (cond == null) return defaultExec;
        int v = cond.getValue(ctx);
        return caseExecs.getOrDefault(v, defaultExec);
    }
}
