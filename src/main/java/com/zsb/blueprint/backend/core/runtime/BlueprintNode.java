package com.zsb.blueprint.backend.core.runtime;

public interface BlueprintNode {
    String getId();
    void execute(ExecutionContext ctx);
}
