package com.lianle.service;

import com.lianle.entity.Language;

import java.util.List;

/**
 * Created by lianle on 2/23 0023.
 */
public interface LanguageService {
    void save(Language language);

    /**
     * 根据名字查询
     * @param name
     * @return
     */
    Language queryByName(String name);

    /**
     * 查询所有语言分类
     * @return
     */
    List<Language> queryList();

    Language queryById(long languageId);
}
