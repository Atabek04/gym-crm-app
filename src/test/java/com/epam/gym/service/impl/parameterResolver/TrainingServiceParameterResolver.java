package com.epam.gym.service.impl.parameterResolver;

import com.epam.gym.dao.impl.TrainingDAOImpl;
import com.epam.gym.service.TrainingService;
import com.epam.gym.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.HashMap;

public class TrainingServiceParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(TrainingService.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var trainingDAO = new TrainingDAOImpl(new HashMap<>());
        return new TrainingServiceImpl(trainingDAO);
    }
}
