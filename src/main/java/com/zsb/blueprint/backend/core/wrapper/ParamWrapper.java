package com.zsb.blueprint.backend.core.wrapper;

/**
 * @param <T> 用来包装参数的装饰器
 */
public class ParamWrapper<T> {
    public T value;

    public ParamWrapper() {
    }

    public ParamWrapper(T value) {
        this.value = value;
    }
}
