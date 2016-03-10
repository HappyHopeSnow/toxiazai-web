package com.lianle.service.impl;

import com.lianle.dao.FilmClassRelDao;
import com.lianle.dao.FormatDao;
import com.lianle.entity.Film;
import com.lianle.entity.FilmClassRel;
import com.lianle.entity.Format;
import com.lianle.service.FilmClassRelService;
import com.lianle.service.FormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("filmClassRelService")
public class FilmClassRelServiceImpl implements FilmClassRelService {
    @Autowired
    private FilmClassRelDao dao;

    public void save(FilmClassRel filmClassRel) {
        dao.save(filmClassRel);
    }
}
