package com.lianle.service;

import com.lianle.entity.Performer;

/**
 * Created by lianle on 2/23 0023.
 */
public interface PerformerService {
    void save(Performer performer);

    /**
     * 根据名称查询格式
     * @param name
     * @return
     */
    Performer queryByName(String name);
}
