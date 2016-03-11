package com.lianle.service.impl;

import com.lianle.dao.CountryDao;
import com.lianle.entity.Country;
import com.lianle.entity.Screen;
import com.lianle.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("countryService")
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryDao dao;

    public void save(Country country) {
        dao.save(country);
    }


    public Country queryByName(String name) {
        String hql = "FROM Country WHERE name = ?";
        List<Country> list = dao.getListByHQL(hql, new Object[]{name});
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
    }

    public List<Country> queryList() {
        String hql = "FROM Country ";
        List<Country> list = dao.getListByHQL(hql);
        if ((list == null) || (list.size() == 0)) {
            return new ArrayList<Country>();
        }
        return list;
    }

    public Country queryById(long classTypeId) {
        String hql = "FROM Country WHERE id = ?";
        List<Country> list = dao.getListByHQL(hql, new Object[]{classTypeId});
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
    }

}
