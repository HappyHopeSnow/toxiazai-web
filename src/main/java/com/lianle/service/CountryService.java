package com.lianle.service;

import com.lianle.entity.Country;

import java.util.List;

/**
 * Created by lianle on 2/23 0023.
 */
public interface CountryService {

    void save(Country country);

    /**
     * 根据名字查询
     * @param name
     * @return
     */
    Country queryByName(String name);

    /**
     * 查询所有的国家分类
     * @return
     */
    List<Country> queryList();

    Country queryById(long countryId);
}
