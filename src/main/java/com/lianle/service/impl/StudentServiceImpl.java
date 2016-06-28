package com.lianle.service.impl;

import com.lianle.dao.StudentDao;
import com.lianle.dao.UserDao;
import com.lianle.entity.Student;
import com.lianle.entity.User;
import com.lianle.service.StudentService;
import com.lianle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("studentService")
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    public void save(Student student) {
        studentDao.save(student);
    }


}
