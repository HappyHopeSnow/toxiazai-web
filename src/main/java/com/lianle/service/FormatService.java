package com.lianle.service;

import com.lianle.entity.Format;

/**
 * Created by lianle on 2/23 0023.
 */
public interface FormatService {

    void save(Format format);

    /**
     * 根据名称查询格式
     * @param formatName
     * @return
     */
    Format queryByName(String formatName);
}
