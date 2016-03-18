package com.lianle.service;

import com.lianle.common.PageResults;
import com.lianle.entity.Advice;

/**
 * Created by lianle on 2/23 0023.
 */
public interface AdviceService {

    void save(Advice advice);

    /**
     * 分页查询建议
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageResults<Advice> queryByPage(int pageNo, int pageSize);
}
