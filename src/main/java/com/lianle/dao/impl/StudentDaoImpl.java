package com.lianle.dao.impl;

import com.lianle.common.BaseDaoImpl;
import com.lianle.dao.StudentDao;
import com.lianle.dao.UserDao;
import com.lianle.entity.Student;
import org.springframework.stereotype.Repository;

/**
 * Created by lianle on 2/13 0013.
 */
@Repository
public class StudentDaoImpl extends BaseDaoImpl implements StudentDao {

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
