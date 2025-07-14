package com.zsb.blueprint.backend.tests;

import com.zsb.blueprint.backend.core.definition.FunctionDefinition;
import com.zsb.blueprint.backend.core.resolver.FunctionResolver;

import java.util.List;

public class ResolverTest {
    public static void main(String[] args) {
        List<FunctionDefinition> results = FunctionResolver.resolveFromClass(MathLibrary.class);
        System.out.printf("results: %s\n", results);
    }
}
