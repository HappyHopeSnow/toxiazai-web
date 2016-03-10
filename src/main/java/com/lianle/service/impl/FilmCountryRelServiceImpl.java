package com.lianle.service.impl;

import com.lianle.dao.FilmCountryRelDao;
import com.lianle.entity.FilmCountryRel;
import com.lianle.service.FilmCountryRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("filmCountryRelService")
public class FilmCountryRelServiceImpl implements FilmCountryRelService {
    @Autowired
    private FilmCountryRelDao dao;

    public void save(FilmCountryRel filmCountryRel) {
        dao.save(filmCountryRel);
    }
}
