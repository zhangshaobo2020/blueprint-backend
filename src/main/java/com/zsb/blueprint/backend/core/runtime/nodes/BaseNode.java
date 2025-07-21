package com.zsb.blueprint.backend.core.runtime.nodes;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public abstract class BaseNode implements Serializable {
    private String id;
    private String name;

    public BaseNode(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private ExecutionContext ctx;
}
