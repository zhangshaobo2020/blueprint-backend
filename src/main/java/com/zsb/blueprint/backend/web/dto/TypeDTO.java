package com.zsb.blueprint.backend.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class TypeDTO {
    private String name;
    private String qualifiedName;
    private boolean primitive;
    private boolean list;
    private boolean map;
    private List<TypeDTO> generics;
    private boolean blueprintType;
    private Map<String, TypeDTO> fields;
}
