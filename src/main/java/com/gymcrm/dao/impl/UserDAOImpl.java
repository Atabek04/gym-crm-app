package com.gymcrm.dao.impl;

import com.gymcrm.dao.AbstractDAO;
import com.gymcrm.dao.UserDAO;
import com.gymcrm.model.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {
    @Autowired
    public UserDAOImpl(Map<Integer, User> userStorage) {
        super(userStorage, LoggerFactory.getLogger(UserDAOImpl.class), "User");
    }

    @Override
    public void save(User user) {
        super.save(user, user.getId());
    }

    @Override
    public void update(User user) {
        super.update(user, user.getId());
    }

    @Override
    public Optional<User> findById(int id) {
        return super.findById(id);
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }
}
