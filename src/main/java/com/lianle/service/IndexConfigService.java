package com.lianle.service;

import com.lianle.entity.IndexConfig;

/**
 * Created by lianle on 2/23 0023.
 */
public interface IndexConfigService {
    void save(IndexConfig indexConfig);


    /**
     * 查询所有记录
     * @return
     */
    IndexConfig queryLast();

    IndexConfig queryById(long id);

    void update(IndexConfig indexConfig);
}
