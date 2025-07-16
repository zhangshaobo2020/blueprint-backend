package com.zsb.blueprint.backend.core.definition.pin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecPin implements Serializable {
    private String name;          // 引脚名，如 Entry、True、False、Loop
    private boolean input;        // true 表示输入，false 表示输出
}
