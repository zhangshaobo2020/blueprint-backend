package com.zsb.blueprint.backend.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NodeMetaDTO implements Serializable {
    private String name;
    private String qualifiedName;
    private String category;
    private List<ExecPinDTO> execPins;
    private List<ParamPinDTO> paramPins;
    private String description;
    private String displayName;
    private boolean executable;
    private Object functionDefinition;
    private List<Object> params;
}
