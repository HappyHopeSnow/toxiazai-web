package com.lianle.service.impl;

import com.lianle.dao.AdviceDao;
import com.lianle.entity.Advice;
import com.lianle.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("adviceService")
public class AdviceServiceImpl implements AdviceService {
    @Autowired
    private AdviceDao dao;

    public void save(Advice advice) {
        dao.save(advice);
    }
}
