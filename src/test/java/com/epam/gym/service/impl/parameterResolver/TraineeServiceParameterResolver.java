package com.epam.gym.service.impl.parameterResolver;

import com.epam.gym.dao.impl.TraineeDAOImpl;
import com.epam.gym.service.TraineeService;
import com.epam.gym.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.HashMap;

public class TraineeServiceParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(TraineeService.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var traineeDAO = new TraineeDAOImpl(new HashMap<>());
        return new TraineeServiceImpl(traineeDAO);
    }
}
