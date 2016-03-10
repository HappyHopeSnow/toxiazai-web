package com.lianle.service.impl;

import com.lianle.dao.FilmLanguageRelDao;
import com.lianle.entity.FilmLanguageRel;
import com.lianle.service.FilmLanguageRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("filmLanguageRelService")
public class FilmLanguageRelServiceImpl implements FilmLanguageRelService {
    @Autowired
    private FilmLanguageRelDao dao;

    public void save(FilmLanguageRel filmLanguageRel) {
        dao.save(filmLanguageRel);
    }
}
