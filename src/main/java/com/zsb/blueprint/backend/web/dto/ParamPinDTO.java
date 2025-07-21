package com.zsb.blueprint.backend.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ParamPinDTO implements Serializable {
    private ParamDefDTO paramDef;
}
