package com.zsb.blueprint.backend.core.definition.pin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowPin implements Serializable {
    private String name;          // 引脚名，如 Entry、True、False、Loop
}
