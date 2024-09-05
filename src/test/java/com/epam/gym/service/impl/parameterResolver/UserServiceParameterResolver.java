package com.epam.gym.service.impl.parameterResolver;

import com.epam.gym.dao.impl.UserDAOImpl;
import com.epam.gym.service.UserService;
import com.epam.gym.service.impl.UserServiceImpl;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.HashMap;

public class UserServiceParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(UserService.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var userDAO = new UserDAOImpl(new HashMap<>());
        return new UserServiceImpl(userDAO);
    }
}
