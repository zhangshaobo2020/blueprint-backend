package com.zsb.blueprint.backend.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class GraphDTO implements Serializable {
    private List<NodeDTO> nodes;
    private List<ConnectionDTO> connections;
}
