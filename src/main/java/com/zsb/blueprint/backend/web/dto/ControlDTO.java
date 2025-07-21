package com.zsb.blueprint.backend.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ControlDTO implements Serializable {
    private String id;
    private Object value;
    private String nodeId;
    private String inputId;
}
