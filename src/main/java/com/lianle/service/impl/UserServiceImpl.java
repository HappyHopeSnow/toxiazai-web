package com.lianle.service.impl;

import com.lianle.dao.UserDao;
import com.lianle.entity.User;
import com.lianle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public void save(User user) {
        userDao.save(user);
    }

    public void saveUsers(List<User> us) {
        for (User u : us) {
            userDao.save(u);
        }
    }

    public List<User> getAllUsernames() {
        String hql = "FROM User";
        List<User> list = userDao.getListByHQL(hql);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list;
    }

    public User getAdmin() {
        String hql = "FROM User WHERE userName = ?";
        List<User> list = userDao.getListByHQL(hql, new Object[]{"lianle"});
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
    }

    public User queryById(Long id) {
        String hql = "FROM User WHERE id = ?";
        List<User> list = userDao.getListByHQL(hql, new Object[]{id});
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
    }
}
