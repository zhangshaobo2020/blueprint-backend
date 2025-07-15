package com.zsb.blueprint.backend.web.controller;

import com.zsb.blueprint.backend.core.config.BlueprintScanner;
import com.zsb.blueprint.backend.core.definition.FunctionDefinition;
import com.zsb.blueprint.backend.core.definition.TypeDefinition;
import com.zsb.blueprint.backend.web.unify.WebResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/blueprint/global")
public class BlueprintGlobalController {

    @PostMapping("/typeDefinition")
    public WebResult<Map<String, TypeDefinition>> typeDefinition() throws Exception {
        return WebResult.success(BlueprintScanner.TYPE_DEFINITION);
    }

    @PostMapping("/functionDefinition")
    public WebResult<Map<String, FunctionDefinition>> functionDefinition() throws Exception {
        return WebResult.success(BlueprintScanner.FUNCTION_DEFINITION);
    }
}
