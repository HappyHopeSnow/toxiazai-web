package com.lianle.service;

import com.lianle.entity.Screen;

import java.util.List;

/**
 * Created by lianle on 2/23 0023.
 */
public interface ScreenService {

    void save(Screen screen);

    /**
     * 根据年份名字查询
     * @param time
     * @return
     */
    Screen queryByName(String time);

    /**
     * 查询所有的
     * @return
     */
    List<Screen> queryList();

    /**
     * 根据主键id查询
     * @param screenYearId
     * @return
     */
    Screen queryById(long screenYearId);
}
