package com.lianle.service.impl;

import com.lianle.dao.LanguageDao;
import com.lianle.entity.Language;
import com.lianle.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("languageService")
public class LanguageServiceImpl implements LanguageService {
    @Autowired
    private LanguageDao dao;

    public void save(Language language) {
        dao.save(language);
    }


    public Language queryByName(String name) {
        String hql = "FROM Language WHERE language = ?";
        List<Language> list = dao.getListByHQL(hql, new Object[]{name});
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
    }

    public List<Language> queryList() {
        String hql = "FROM Language";
        List<Language> list = dao.getListByHQL(hql);
        if ((list == null) || (list.size() == 0)) {
            return new ArrayList<Language>();
        }
        return list;
    }

    public Language queryById(long languageId) {
        String hql = "FROM Language WHERE id = ?";
        List<Language> list = dao.getListByHQL(hql, new Object[]{languageId});
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
    }

}
