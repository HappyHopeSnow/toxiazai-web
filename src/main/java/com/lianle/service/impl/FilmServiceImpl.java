package com.lianle.service.impl;

import com.lianle.common.PageResults;
import com.lianle.dao.FilmDao;
import com.lianle.entity.Film;
import com.lianle.service.FilmService;
import com.lianle.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Service("filmService")
public class FilmServiceImpl implements FilmService {
    @Autowired
    private FilmDao dao;

    public void save(Film film) {
        dao.save(film);
    }

    public PageResults<Film> queryByPage(int pageNo, int pageSize) {
        String hql = "FROM Film ORDER BY createTime DESC ";
        String countHql = "SELECT COUNT(*) FROM Film ";
        PageResults<Film> filmPageResults = dao.findPageByFetchedHql(hql, countHql, pageNo, pageSize, new Object[]{});
        return filmPageResults;
    }

    public Film queryById(long id) {
        String hql = "FROM Film WHERE id = ?";
        List<Film> list = dao.getListByHQL(hql, new Object[]{id});
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list.get(0);
    }

    public List<Film> queryByScreenId(long screenYearId) {
        String hql = "FROM Film WHERE screen_year_id = ? ORDER BY createTime DESC ";
        List<Film> list = dao.getListByHQL(hql, new Object[]{screenYearId});
        if ((list == null) || (list.size() == 0)) {
            return new ArrayList<Film>();
        }
        return list;
    }

    public List<Film> queryByClassTypeId(long classTypeId, int pageNo, int pageSize) {
        String hql = "FROM Film WHERE class_id = ? ORDER BY createTime DESC ";
        String countHql = "SELECT COUNT(*) FROM Film WHERE class_id = ?";
        PageResults<Film> filmPageResults = dao.findPageByFetchedHql(hql, countHql, pageNo, pageSize, new Object[]{classTypeId});
        List<Film> list = filmPageResults.getResults();
        if ((list == null) || (list.size() == 0)) {
            return new ArrayList<Film>();
        }
        return list;
    }

    public List<Film> queryByCountryId(long countryId) {
        String hql = "FROM Film WHERE country_id = ? ORDER BY createTime DESC ";
        List<Film> list = dao.getListByHQL(hql, new Object[]{countryId});
        if ((list == null) || (list.size() == 0)) {
            return new ArrayList<Film>();
        }
        return list;
    }

    public List<Film> queryByLanguageId(long languageId) {
        String hql = "FROM Film WHERE language_id = ? ORDER BY createTime DESC ";
        List<Film> list = dao.getListByHQL(hql, new Object[]{languageId});
        if ((list == null) || (list.size() == 0)) {
            return new ArrayList<Film>();
        }
        return list;
    }

    public PageResults<Film> queryByDownCount(int pageNo, int pageSize) {
        String hql = "FROM Film ORDER BY createTime DESC ";
        String countHql = "SELECT COUNT(*) FROM Film ";
        PageResults<Film> filmPageResults = dao.findPageByFetchedHql(hql, countHql, pageNo, pageSize, new Object[]{});
        return filmPageResults;
    }

    public PageResults<Film> search(String key, int pageNo, int pageSize) {
        String hql = "FROM Film WHERE key_word   LIKE '%"+ key +"%' ORDER BY createTime DESC ";
        String countHql = "SELECT COUNT(*) FROM Film WHERE key_word LIKE '%"+ key +"%'";
        PageResults<Film> filmPageResults = dao.findPageByFetchedHql(hql, countHql, pageNo, pageSize, new Object[]{});
        return filmPageResults;
    }

    public List<Film> queryInStringIds(String ids) {
        String sql = "FROM Film WHERE id IN ("+ ids +")";
        System.out.println("sql is[" + sql + "]");
        List<Film> list = dao.getListByHQL(sql);
        return list;
    }

    public List<Film> queryInLongIds(Long[] ids) {
        String sql = "FROM Film WHERE id IN ("+ StringUtils.join(ids, ",")+")";
        System.out.println("sql is[" + sql + "]");
        List<Film> list = dao.getListByHQL(sql);
        return list;
    }

    public Film queryByMaxId() {
//        select * from curl_log order by create_time desc limit 1;
        String hql = "FROM Film ORDER BY id DESC ";
        String countHql = "SELECT COUNT(*) FROM Film ";
        PageResults<Film> filmPageResults = dao.findPageByFetchedHql(hql, countHql, 1, 1, new Object[]{});
        return filmPageResults.getResults().get(0);
    }

    public List<Film> getAllFilms() {
        String hql = "FROM Film";
        List<Film> list = dao.getListByHQL(hql);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return list;
    }
}
