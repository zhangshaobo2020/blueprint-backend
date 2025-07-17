package com.zsb.blueprint.backend.core.resolver;

import com.zsb.blueprint.backend.core.annotations.BlueprintFunction;
import com.zsb.blueprint.backend.core.annotations.ParamInput;
import com.zsb.blueprint.backend.core.annotations.ParamOutput;
import com.zsb.blueprint.backend.core.definition.FunctionDefinition;
import com.zsb.blueprint.backend.core.definition.ParamDefinition;
import com.zsb.blueprint.backend.core.definition.TypeDefinition;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;
import org.apache.commons.lang3.StringUtils;

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
            if (bf == null) continue;

            FunctionDefinition func = new FunctionDefinition();
            func.setName(method.getName());
            func.setQualifiedName(clazz.getSimpleName() + "." + method.getName());
            if (StringUtils.isNotBlank(bf.category())) {
                func.setCategory(bf.category());
            } else {
                func.setCategory(clazz.getSimpleName());
            }
            func.setDescription(bf.description());
            func.setParams(new ArrayList<>());
            func.setExecutable(bf.executable());
            func.setLatent(bf.latent());
            func.setDisplayName(bf.displayName());

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
        ParamInput paramInput = parameter.getAnnotation(ParamInput.class);
        if (paramInput != null) return paramInput.value();
        ParamOutput paramOutput = parameter.getAnnotation(ParamOutput.class);
        if (paramOutput != null) return paramOutput.value();
        throw new IllegalArgumentException("参数必须由@ParamInput或@ParamOutput注解，但实际是" + parameter.getName());
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

        throw new IllegalArgumentException("参数类型必须是ParamWrapper<T>，但实际是" + paramType);
    }
}

