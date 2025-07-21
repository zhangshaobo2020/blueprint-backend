package com.zsb.blueprint.backend.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
public class NodeDTO implements Serializable {
    private Map<String, IOEntryDTO> inputs;
    private Map<String, IOEntryDTO> outputs;
    private Map<String, Object> controls;
    private String label;
    private String id;
    private NodeMetaDTO meta;
    private boolean hasExec;
    private boolean selected;
}
