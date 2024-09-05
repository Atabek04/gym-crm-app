package com.epam.gym.service.impl.parameterResolver;

import com.epam.gym.dao.impl.TrainerDAOImpl;
import com.epam.gym.service.TrainerService;
import com.epam.gym.service.impl.TrainerServiceImpl;
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
