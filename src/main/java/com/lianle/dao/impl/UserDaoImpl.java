package com.lianle.dao.impl;

import com.lianle.common.BaseDao;
import com.lianle.dao.UserDao;
import com.lianle.entity.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Repository
public class UserDaoImpl extends BaseDao implements UserDao {

//    @Autowired
//    private SessionFactory sessionFactory;
//
//    public int save(User u) {
//        return (Integer) sessionFactory.getCurrentSession().save(u);
//    }
//
//    public void update(User user) {
//       sessionFactory.getCurrentSession().update(user);
//    }
//
//    public List<User> findAll() {
//        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
//        return criteria.list();
//    }
}
