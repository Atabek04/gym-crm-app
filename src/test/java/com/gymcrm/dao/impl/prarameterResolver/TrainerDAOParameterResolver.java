package com.gymcrm.dao.impl.prarameterResolver;

import com.gymcrm.dao.TrainerDAO;
import com.gymcrm.dao.impl.TrainerDAOImpl;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.HashMap;

public class TrainerDAOParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(TrainerDAO.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new TrainerDAOImpl(new HashMap<>());
    }
}
