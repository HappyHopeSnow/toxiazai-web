package com.lianle.service.impl;

import com.lianle.dao.FilmPerformRelDao;
import com.lianle.entity.FilmPerformerRel;
import com.lianle.service.FilmPerformerRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("filmPerformerRelService")
public class FilmPerformerRelServiceImpl implements FilmPerformerRelService {
    @Autowired
    private FilmPerformRelDao dao;

    public void save(FilmPerformerRel filmPerformerRel) {
        dao.save(filmPerformerRel);
    }
}
