package com.epam.gym.dao.impl;

import com.epam.gym.dao.AbstractDAO;
import com.epam.gym.dao.UserDAO;
import com.epam.gym.model.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {
    @Autowired
    public UserDAOImpl(Map<Long, User> userStorage) {
        super(userStorage, LoggerFactory.getLogger(UserDAOImpl.class), "User");
    }
}
