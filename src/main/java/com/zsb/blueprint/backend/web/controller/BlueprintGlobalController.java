package com.zsb.blueprint.backend.web.controller;

import com.zsb.blueprint.backend.core.config.BlueprintTypeScanner;
import com.zsb.blueprint.backend.core.definition.TypeDefinition;
import com.zsb.blueprint.backend.web.unify.WebResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/blueprint/global")
public class BlueprintGlobalController {

    @Resource
    private BlueprintTypeScanner blueprintTypeScanner;

    @PostMapping("/test")
    public WebResult<Map<String, TypeDefinition>> test() throws Exception {
        Map<String, TypeDefinition> definitionMap = blueprintTypeScanner.getTypeMap();
        return WebResult.success(definitionMap);
    }
}
