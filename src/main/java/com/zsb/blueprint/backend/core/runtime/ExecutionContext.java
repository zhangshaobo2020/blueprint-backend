package com.zsb.blueprint.backend.core.runtime;

import com.zsb.blueprint.backend.core.runtime.nodes.ExecNode;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ExecutionContext {
    private final Map<String, ExecNode> nodes = new HashMap<>();
    private final Map<String, ParamWrapper<?>> dataSlots = new HashMap<>();
    private boolean breakFlag = false;

    public void addNode(ExecNode node) {
        nodes.put(node.getId(), node);
    }

    public ExecNode getNode(String id) {
        return nodes.get(id);
    }

    public void setBreak() {
        breakFlag = true;
    }

    public boolean isBreak() {
        return breakFlag;
    }

    // 核心：从 startNode 开始执行
    public void run(String startNodeId) {
        String currentId = startNodeId;
        while (currentId != null) {
            ExecNode node = nodes.get(currentId);
            long start = System.currentTimeMillis();
            currentId = node.execute(this);
            long end = System.currentTimeMillis();
            System.out.println("当前节点为 " + node.getId() + ",执行时间为 " + (end - start));
        }
    }
}
