package com.lianle.service.impl;

import com.lianle.dao.ClassTypeDao;
import com.lianle.entity.ClassType;
import com.lianle.entity.Country;
import com.lianle.service.ClassTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("classTypeService")
public class ClassTypeServiceImpl implements ClassTypeService {
    @Autowired
    private ClassTypeDao dao;

    public void save(ClassType classType) {
        dao.save(classType);
    }


    public ClassType queryByName(String name) {
        String hql = "FROM ClassType WHERE class_name = ?";
        List<ClassType> list = dao.getListByHQL(hql, new Object[]{name});
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
    }

}
