package com.lianle.service.impl;

import com.lianle.dao.CommentDao;
import com.lianle.dao.UserDao;
import com.lianle.entity.Comment;
import com.lianle.entity.User;
import com.lianle.service.CommentService;
import com.lianle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao dao;

    public void save(Comment comment) {
        dao.save(comment);
    }

    public Comment queryById(Long id) {
        String hql = "FROM Comment WHERE id = ?";
        List<Comment> list = dao.getListByHQL(hql, new Object[]{id});
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
    }

    public List<Comment> queryByFilmId(Long filmId) {
        String hql = "FROM Comment WHERE film_id  = ? ORDER BY createTime DESC ";
        List<Comment> list = dao.getListByHQL(hql, new Object[]{filmId});
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list;
    }

    public int queryCountByFilmId(Long filmId) {
        String hql = "SELECT COUNT(id) FROM Comment WHERE film_id  = ? ";
        List<Integer> list = dao.getListByHQL(hql, new Object[]{filmId});
        if ((list == null) || (list.size() == 0)) {
            return 0;
        }
        return list.get(0);
    }
}
