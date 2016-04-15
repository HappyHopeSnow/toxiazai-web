package com.lianle.service.impl;

import com.lianle.dao.CurlLogDao;
import com.lianle.dao.LanguageDao;
import com.lianle.entity.CurlLog;
import com.lianle.entity.Language;
import com.lianle.service.CurlLogService;
import com.lianle.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("curlLogService")
public class CurlLogServiceImpl implements CurlLogService {
    @Autowired
    private CurlLogDao dao;

    public void save(CurlLog curlLog) {
        dao.save(curlLog);
    }

    public CurlLog queryLast() {
        String hql = "FROM CurlLog ORDER BY createTime DESC ";
        List<CurlLog> list = dao.getListByHQL(hql);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
    }

    public void update(CurlLog curlLog) {
        dao.update(curlLog);
    }
}
