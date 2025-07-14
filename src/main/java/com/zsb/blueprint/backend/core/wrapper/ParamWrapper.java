package com.zsb.blueprint.backend.core.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @param <T> 用来包装参数的装饰器
 */
@Data
@NoArgsConstructor
public class ParamWrapper<T> {
    private T value;
}
