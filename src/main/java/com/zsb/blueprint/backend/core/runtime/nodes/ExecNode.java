package com.zsb.blueprint.backend.core.runtime.nodes;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ExecNode {
    protected String id;

    // 返回下一个节点 ID
    public abstract String execute(ExecutionContext ctx);
}
