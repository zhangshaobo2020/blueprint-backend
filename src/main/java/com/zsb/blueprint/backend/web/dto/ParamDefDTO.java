package com.zsb.blueprint.backend.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ParamDefDTO implements Serializable {
    private String name;
    private boolean input;
    private TypeDTO type;
}
