package com.lianle.service;

import com.lianle.entity.Comment;

import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
public interface CommentService {

    void save(Comment comment);

    Comment queryById(Long id);

    /**
     * 查询当前电影下的所有评论
     * @param filmId
     * @return
     */
    List<Comment> queryByFilmId(Long filmId);

    /**
     * 查询当前电影下的所有评论数量
     * @param filmId
     * @return
     */
    int queryCountByFilmId(Long filmId);
}
