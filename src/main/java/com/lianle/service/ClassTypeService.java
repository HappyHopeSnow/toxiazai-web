package com.lianle.service;

import com.lianle.entity.ClassType;

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
}
