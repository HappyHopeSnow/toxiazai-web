package com.lianle.service;

import com.lianle.entity.Country;

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
}
