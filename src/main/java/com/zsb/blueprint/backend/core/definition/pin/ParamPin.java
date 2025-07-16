package com.zsb.blueprint.backend.core.definition.pin;

import com.zsb.blueprint.backend.core.definition.ParamDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamPin implements Serializable {
    private ParamDefinition paramDef;   // 参数定义(包含名称)
}
