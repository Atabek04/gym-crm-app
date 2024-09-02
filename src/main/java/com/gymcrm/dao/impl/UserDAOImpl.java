package com.gymcrm.dao.impl;

import com.gymcrm.dao.AbstractDAO;
import com.gymcrm.dao.UserDAO;
import com.gymcrm.model.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {
    @Autowired
    public UserDAOImpl(Map<Integer, User> userStorage) {
        super(userStorage, LoggerFactory.getLogger(UserDAOImpl.class), "User");
    }
}
