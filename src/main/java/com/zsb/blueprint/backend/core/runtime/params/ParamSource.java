package com.zsb.blueprint.backend.core.runtime.params;

import com.zsb.blueprint.backend.core.runtime.ExecutionContext;

public interface ParamSource<T> {
    T getValue(ExecutionContext ctx);
}
