package com.lianle.service;

import com.lianle.common.PageResults;
import com.lianle.entity.Film;

import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
public interface FilmService {
    void save(Film film);

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageResults<Film> queryByPage(int pageNo, int pageSize);

    /**
     * 通过主键查询
     * @param id
     * @return
     */
    Film queryById(long id);

    /**
     * 根据上映年份查询
     * @param screenYearId
     * @return
     */
    List<Film> queryByScreenId(long screenYearId);

    /**
     * 根据所属类型id查询
     * @param classTypeId
     * @return
     */
    List<Film> queryByClassTypeId(long classTypeId, int pageNo, int pageSize);

    /**
     * 根据国家查询
     * @param countryId
     * @return
     */
    List<Film> queryByCountryId(long countryId);

    /**
     * 根据语言查询
     * @param languageId
     * @return
     */
    List<Film> queryByLanguageId(long languageId);

    /**
     * 根据下载排行榜查询
     * @return
     */
    PageResults<Film> queryByDownCount(int pageNo, int pageSize);

    /**
     * 搜索
     * @param key
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageResults<Film> search(String key, int pageNo, int pageSize);

    /**
     * 查询id在里面的
     * @param ids:字符串组合，逗号分割
     * @return
     */
    List<Film> queryInStringIds(String ids);

    /**
     * 查询id在里面的
     * @param ids Long数组
     * @return
     */
    List<Film> queryInLongIds(Long[] ids);

    /**
     * 查询最大的id
     * @return
     */
    Film queryByMaxId();

}
