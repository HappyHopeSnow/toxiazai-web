package com.lianle.service.impl;

import com.lianle.dao.FilmDao;
import com.lianle.entity.Film;
import com.lianle.entity.User;
import com.lianle.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("filmService")
public class FilmServiceImpl implements FilmService {
    @Autowired
    private FilmDao dao;

    public void save(Film film) {
        dao.save(film);
    }

    public List<Film> getAllFilms() {
        String hql = "FROM Film";
        List<Film> list = dao.getListByHQL(hql);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list;
    }
}
