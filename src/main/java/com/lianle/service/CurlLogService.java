package com.lianle.service;

import com.lianle.entity.CurlLog;
import com.lianle.entity.Language;

import java.util.List;

/**
 * Created by lianle on 2/23 0023.
 */
public interface CurlLogService {
    void save(CurlLog curlLog);

    /**
     * 查询上一次最新的记录
     * @return
     */
    CurlLog queryLast();

}
