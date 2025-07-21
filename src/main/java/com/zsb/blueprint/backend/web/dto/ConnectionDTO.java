package com.zsb.blueprint.backend.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ConnectionDTO implements Serializable {
    private String id;
    private String source;
    private String sourceOutput;
    private String target;
    private String targetInput;
}
