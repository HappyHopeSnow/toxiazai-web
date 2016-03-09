package com.lianle.service.impl;

import com.lianle.dao.ScreenDao;
import com.lianle.entity.Screen;
import com.lianle.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("screenService")
public class ScreenServiceImpl implements ScreenService {
    @Autowired
    private ScreenDao dao;

    public void save(Screen screen) {
        dao.save(screen);
    }

    public Screen queryByName(String time) {
        String hql = "FROM Screen WHERE screen_year = ?";
        List<Screen> list = dao.getListByHQL(hql, new Object[]{new Integer(time)});
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
    }

}
