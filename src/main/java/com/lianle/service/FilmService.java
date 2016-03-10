package com.lianle.service;

import com.lianle.common.PageResults;
import com.lianle.entity.Film;

import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
public interface FilmService {
    void save(Film film);

    /**
     * 分页查询
     * @param start
     * @param size
     * @return
     */
    PageResults<Film> queryByPage(int start, int size);

    /**
     * 通过主键查询
     * @param id
     * @return
     */
    Film queryById(long id);
}
