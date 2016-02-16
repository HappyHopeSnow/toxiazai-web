package com.lianle.service;

import com.lianle.entity.User;

import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
public interface UserService {
    void save(User user);
    public void saveUsers(List<User> us);
    public List<User> getAllUsernames();
}
