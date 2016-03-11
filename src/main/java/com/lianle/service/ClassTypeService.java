package com.lianle.service;

import com.lianle.entity.ClassType;

import java.util.List;

/**
 * Created by lianle on 2/23 0023.
 */
public interface ClassTypeService {
    void save(ClassType classType);

    /**
     * 根据名字查询
     * @param name
     * @return
     */
    ClassType queryByName(String name);

    /**
     * 查询所有的类型分类
     * @return
     */
    List<ClassType> queryList();

    ClassType queryById(long classTypeId);
}
