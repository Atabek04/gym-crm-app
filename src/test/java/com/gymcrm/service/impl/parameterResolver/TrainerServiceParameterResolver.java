package com.gymcrm.service.impl.parameterResolver;

import com.gymcrm.dao.impl.TrainerDAOImpl;
import com.gymcrm.service.TrainerService;
import com.gymcrm.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.HashMap;

public class TrainerServiceParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(TrainerService.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var trainerDAO = new TrainerDAOImpl(new HashMap<>());
        return new TrainerServiceImpl(trainerDAO);
    }
}
