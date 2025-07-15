package com.zsb.blueprint.backend.web.unify;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class WebResult<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public static <T> WebResult<T> success(T data) {
        return new WebResult<>(200, null, data);
    }

    public static <T> WebResult<T> failure(String msg) {
        return new WebResult<>(-1, msg, null);
    }
}
