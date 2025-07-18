package com.zsb.blueprint.backend.core.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @param <T> 用来包装参数的装饰器
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamWrapper<T> {
    private T value;
}
