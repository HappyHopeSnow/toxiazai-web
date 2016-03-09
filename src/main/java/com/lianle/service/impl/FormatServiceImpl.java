package com.lianle.service.impl;

import com.lianle.dao.FormatDao;
import com.lianle.entity.Film;
import com.lianle.entity.Format;
import com.lianle.service.FormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("formatService")
public class FormatServiceImpl implements FormatService {
    @Autowired
    private FormatDao dao;

    public void save(Format format) {
        dao.save(format);
    }

    public Format queryByName(String formatName) {
        String hql = "FROM Format WHERE format_name = ?";
        List<Format> list = dao.getListByHQL(hql, new Object[]{formatName});
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
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
