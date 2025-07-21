package com.zsb.blueprint.backend.core.runtime.params;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LiteralValueSource<T> implements ParamSource<T> {
    private final T value;

    @Override
    public T getValue(ExecutionContext ctx) {
        return value;
    }
}
