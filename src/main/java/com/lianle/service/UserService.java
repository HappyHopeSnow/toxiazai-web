package com.lianle.service;

import com.lianle.entity.User;

import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
public interface UserService {
    void save(User user);
    void saveUsers(List<User> us);
    List<User> getAllUsernames();

    /**
     * 得到管理员
     * @return
     */
    User getAdmin();

}
