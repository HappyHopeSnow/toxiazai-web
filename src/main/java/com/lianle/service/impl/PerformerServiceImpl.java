package com.lianle.service.impl;

import com.lianle.dao.PerformerDao;
import com.lianle.entity.Country;
import com.lianle.entity.Performer;
import com.lianle.service.PerformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("performerService")
public class PerformerServiceImpl implements PerformerService {
    @Autowired
    private PerformerDao dao;

    public void save(Performer performer) {
        dao.save(performer);
    }


    public Performer queryByName(String name) {
        String hql = "FROM Performer WHERE name = ?";
        List<Performer> list = dao.getListByHQL(hql, new Object[]{name});
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
    }

}
