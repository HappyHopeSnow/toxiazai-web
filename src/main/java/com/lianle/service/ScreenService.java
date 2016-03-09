package com.lianle.service;

import com.lianle.entity.Screen;

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
}
