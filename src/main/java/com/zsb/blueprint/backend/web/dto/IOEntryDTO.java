package com.zsb.blueprint.backend.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class IOEntryDTO implements Serializable {
    private SocketDTO socket;
    private String label;
    private boolean multipleConnections;
    private String id;
    private ControlDTO control;
    private boolean showControl;
    private IOEntryMetaDTO meta;
}
