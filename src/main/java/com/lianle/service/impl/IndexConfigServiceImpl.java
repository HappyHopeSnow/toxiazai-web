package com.lianle.service.impl;

import com.lianle.dao.IndexConfigDao;
import com.lianle.entity.IndexConfig;
import com.lianle.service.IndexConfigService;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("indexConfigService")
public class IndexConfigServiceImpl implements IndexConfigService {
    @Autowired
    private IndexConfigDao dao;

    public void save(IndexConfig indexConfig) {
        dao.save(indexConfig);
    }

    public IndexConfig queryLast() {
        String hql = "FROM IndexConfig ORDER BY createTime DESC ";
        List<IndexConfig> list = dao.getListByHQL(hql);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
    }

    public IndexConfig queryById(long id) {
        String hql = "FROM IndexConfig WHERE id = ?";
        List<IndexConfig> list = dao.getListByHQL(hql, new Object[]{id});
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
    }

    public void update(IndexConfig indexConfig) {
            String hql = "update IndexConfig ic set ic.recommend_ids=?, ic.array_ids=?, ic.new_ids = ?," +
                    "ic.hot_ids=?, ic.love_ids = ?, ic.createTime = ? where ic.id=?";
            dao.queryHql(hql, new Object[]{indexConfig.getRecommend_ids(), indexConfig.getArray_ids(), indexConfig.getNew_ids(), indexConfig.getHot_ids(), indexConfig.getLove_ids(), new Date(), indexConfig.getId()});
    }
}
