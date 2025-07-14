package com.zsb.blueprint.backend.core.resolver;

import com.zsb.blueprint.backend.core.annotations.BlueprintFunction;
import com.zsb.blueprint.backend.core.annotations.ParamInput;
import com.zsb.blueprint.backend.core.annotations.ParamOutput;
import com.zsb.blueprint.backend.core.definition.FunctionDefinition;
import com.zsb.blueprint.backend.core.definition.ParamDefinition;
import com.zsb.blueprint.backend.core.definition.TypeDefinition;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionResolver {

    public static List<FunctionDefinition> resolveFromClass(Class<?> clazz) {
        List<FunctionDefinition> functions = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {
            if (!Modifier.isStatic(method.getModifiers())) continue;
            if (!method.isAnnotationPresent(BlueprintFunction.class)) continue;

            BlueprintFunction bf = method.getAnnotation(BlueprintFunction.class);

            FunctionDefinition func = new FunctionDefinition();
            func.setName(method.getName());
            func.setQualifiedName(clazz.getSimpleName() + "." + method.getName());
            func.setCategory(bf.category());
            func.setDescription(bf.description());
            func.setParams(new ArrayList<>());

            Parameter[] parameters = method.getParameters();
            for (Parameter param : parameters) {
                ParamDefinition paramDef = new ParamDefinition();
                paramDef.setName(getParamName(param));
                paramDef.setInput(param.isAnnotationPresent(ParamInput.class));
                paramDef.setType(resolveParamType(param.getParameterizedType()));

                func.getParams().add(paramDef);
            }

            functions.add(func);
        }

        return functions;
    }

    private static String getParamName(Parameter parameter) {
        if (parameter.isAnnotationPresent(ParamInput.class)) {
            return parameter.getAnnotation(ParamInput.class).value();
        }
        if (parameter.isAnnotationPresent(ParamOutput.class)) {
            return parameter.getAnnotation(ParamOutput.class).value();
        }
        return parameter.getName(); // fallback
    }

    private static TypeDefinition resolveParamType(Type paramType) {
        // 期待为 ParamWrapper<T>
        if (paramType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) paramType;
            if (pt.getRawType() instanceof Class<?>) {
                Class<?> rawClass = (Class<?>) pt.getRawType();
                if (ParamWrapper.class.isAssignableFrom(rawClass)) {
                    Type actualType = pt.getActualTypeArguments()[0];
                    return TypeResolver.resolveType(actualType);
                }
            }
        }

        throw new IllegalArgumentException("参数类型必须是 ParamWrapper<T>，但发现了：" + paramType);
    }
}

