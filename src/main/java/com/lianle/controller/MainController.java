package com.lianle.controller;

import com.lianle.common.PageResults;
import com.lianle.entity.*;
import com.lianle.service.*;
import com.lianle.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Controller
@RequestMapping("/")
public class MainController {

    private static String screenYearIntroduction;//上映年份
    private static String classTypeIntroduction;//所属类型
    private static String counttryIntroduction;//所属国家
    private static String languageIntroduction;//所属语言

    static {
        screenYearIntroduction = "此处展示了【上映年份】分类相关电影分类详情信息！如果有任何好的意见或者建议，请在【关于】切换页给我们留言，谢谢！";
        classTypeIntroduction = "此处展示了【类型名称】分类相关电影分类详情信息！如果有任何好的意见或者建议，请在【关于】切换页给我们留言，谢谢！";
        counttryIntroduction = "此处展示了【所属国家】分类相关的电影详情信息！如果有任何好的意见或者建议，请在【关于】切换页给我们留言，谢谢！";
        languageIntroduction = "此处展示了【语言】分类相关电影分类详情信息！如果有任何好的意见或者建议，请在【关于】切换页给我们留言，谢谢！";
    }

    @Autowired
    private UserService userService;

    @Autowired
    FilmService filmService;

    @Autowired
    ScreenService screenService;

    @Autowired
    LanguageService languageService;

    @Autowired
    CountryService countryService;

    @Autowired
    ClassTypeService classTypeService;

    @Autowired
    CommentService commentService;

    @Autowired
    AdviceService adviceService;

    @Autowired
    IndexConfigService indexConfigService;

    /**
     * 默认首页
     * @return
     */
    @RequestMapping("")
    public String home(){
        return "index";
    }

    /**
     * 首页
     * @param pageSize
     * @param pageNo
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "index")
    public String home(@RequestParam(value = "size", required = false, defaultValue = "5") int pageSize,
                       @RequestParam(value = "no", required = false, defaultValue = "1") int pageNo,
                       ModelMap model) {

        //首页分页数据(8个)
        PageResults<Film> resultList = filmService.queryByPage(pageNo, pageSize);
        List<Film> filmList = resultList.getResults();
        model.addAttribute("filmList", filmList);

        IndexConfig indexConfig = indexConfigService.queryLast();

        if (indexConfig != null) {
            //推荐列表横幅(10个)
            List<Film> recommendFilms = filmService.queryInStringIds(indexConfig.getRecommend_ids());

            //排行榜v
            List<Film> arrayFilms = filmService.queryInStringIds(indexConfig.getArray_ids());

            //最新上映v
            List<Film> newFilms = filmService.queryInStringIds(indexConfig.getNew_ids());

            //最热电影v
            List<Film> hotFilms = filmService.queryInStringIds(indexConfig.getHot_ids());

            //猜你喜欢v
            List<Film> loveFilms = filmService.queryInStringIds(indexConfig.getLove_ids());

            //归档分类-暂时不做

            model.addAttribute("recommendFilms", recommendFilms);
            model.addAttribute("arrayFilms", arrayFilms);
            model.addAttribute("newFilms", newFilms);
            model.addAttribute("hotFilms", hotFilms);
            model.addAttribute("loveFilms", loveFilms);
        }

        //分页信息
        model.addAttribute("current", resultList.getCurrentPage());
        model.addAttribute("pageCount", resultList.getPageCount());
        model.addAttribute("pageSize", resultList.getPageSize());
        model.addAttribute("pageNo", resultList.getPageNo());
        return "index";
    }

    /**
     * 详情页
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "single")
    public String single(@RequestParam(value = "id", required = true) long id,
                         ModelMap model) {

        Film film = filmService.queryById(id);
        model.addAttribute("film", film);

        IndexConfig indexConfig = indexConfigService.queryLast();

        if (indexConfig != null) {

            //排行榜v
            List<Film> arrayFilms = filmService.queryInStringIds(indexConfig.getArray_ids());

            //最新上映v
            List<Film> newFilms = filmService.queryInStringIds(indexConfig.getNew_ids());

            //最热电影v
            List<Film> hotFilms = filmService.queryInStringIds(indexConfig.getHot_ids());

            //猜你喜欢v
            List<Film> loveFilms = filmService.queryInStringIds(indexConfig.getLove_ids());

            //归档分类-暂时不做

            model.addAttribute("arrayFilms", arrayFilms);
            model.addAttribute("newFilms", newFilms);
            model.addAttribute("hotFilms", hotFilms);
            model.addAttribute("loveFilms", loveFilms);
        }

        //查询评论
        List<Comment> comments = commentService.queryByFilmId(id);
        int commentsCount = 0;
        if (comments != null && comments.size() > 0) {
            commentsCount = comments.size();
        }

        //查询相关电影
        List<Film> likeFilms = filmService.queryByClassTypeId(film.getClass_id(), 1, 8);
        model.addAttribute("likeFilms", likeFilms);

        model.addAttribute("comments", comments);
        model.addAttribute("commentsCount", commentsCount);
        return "single";
    }

    /**
     * 进入下载页面
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "singlepage")
    public String singlepage(@RequestParam(value = "id", required = true) long id,
                             ModelMap model) {
        Film film = filmService.queryById(id);

        //查询相关电影
        List<Film> likeFilms = filmService.queryByClassTypeId(film.getClass_id(), 1, 8);
        model.addAttribute("likeFilms", likeFilms);

        //查询评论
        List<Comment> comments = commentService.queryByFilmId(id);
        int commentsCount = 0;
        if (comments != null && comments.size() > 0) {
            commentsCount = comments.size();
        }

        model.addAttribute("likeFilms", likeFilms);
        model.addAttribute("comments", comments);
        model.addAttribute("commentsCount", commentsCount);
        model.addAttribute("film", film);
        return "singlepage";
    }

    /**
     * 关于
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "contact")
    public String contact(ModelMap model) {
        model.addAttribute("message", "Hello world!");
        return "contact";
    }

    /**
     * ajax提交意见
     * @param name
     * @param name
     * @param title
     * @param adviceContent
     * @return
     */
    @RequestMapping("save_advice")
    @ResponseBody
    public UnifiedResponse saveAdvice(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "advice", required = true) String adviceContent){

        UnifiedResponse unifiedResponse = new UnifiedResponse();

        Advice advice = new Advice();
        advice.setName(name);
        advice.setEmail(email);
        advice.setTitle(title);
        advice.setAdvice(adviceContent);
        advice.setCreateTime(new Date());
        advice.setStatus(10);
        adviceService.save(advice);

        unifiedResponse.setStatus(UnifiedResponseCode.RC_SUCC);
        unifiedResponse.setMessage("保存成功");
        return unifiedResponse;
    }

    /**
     * 跳转到分类列表
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "archives")
    public String archives(ModelMap model) {

        //上映年份
        List<Screen> screens = screenService.queryList();

        //语言
        List<Language> languages = languageService.queryList();

        //国家
        List<Country> countries = countryService.queryList();

        //类型
        List<ClassType> classTypes = classTypeService.queryList();

        model.addAttribute("screens", screens);
        model.addAttribute("languages", languages);
        model.addAttribute("countries", countries);
        model.addAttribute("classTypes", classTypes);

        return "archives";
    }

    /**
     * 分类展示的详情列表
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "magazine")
    public String magazine(@RequestParam(value = "screen_year", required = false, defaultValue = "0") long screenYearId,
                           @RequestParam(value = "class_type", required = false, defaultValue = "0") long classTypeId,
                           @RequestParam(value = "country", required = false, defaultValue = "0") long countryId,
                           @RequestParam(value = "language", required = false, defaultValue = "0") long languageId,
                           ModelMap model) {

        final List<Film> filmList;

        //上映年份
        if(screenYearId != 0 && !"".equals(screenYearId)) {
            filmList = filmService.queryByScreenId(screenYearId);

            Screen screen = screenService.queryById(screenYearId);
            model.addAttribute("key", screen.getScreen_year());
            model.addAttribute("message", screenYearIntroduction);

            model.addAttribute("filmList", filmList);
            return "magazine";
        }

        //所属类型
        if(classTypeId != 0 && !"".equals(classTypeId)) {
            filmList = filmService.queryByClassTypeId(classTypeId, 1, 8);

            ClassType classType = classTypeService.queryById(classTypeId);
            model.addAttribute("key", classType.getClass_name());
            model.addAttribute("message", classTypeIntroduction);

            model.addAttribute("filmList", filmList);
            return "magazine";
        }

        //所属国家
        if(countryId != 0 && !"".equals(countryId)) {
            filmList = filmService.queryByCountryId(countryId);
            Country country = countryService.queryById(countryId);
            model.addAttribute("key", country.getName());
            model.addAttribute("message", counttryIntroduction);
            model.addAttribute("filmList", filmList);
            return "magazine";
        }

        //所属语言
        if(languageId != 0 && !"".equals(languageId)) {
            filmList = filmService.queryByLanguageId(languageId);
            Language language = languageService.queryById(languageId);
            model.addAttribute("key", language.getLanguage());
            model.addAttribute("message", languageIntroduction);
            model.addAttribute("filmList", filmList);
            return "magazine";
        }

        filmList = new ArrayList<Film>();
        model.addAttribute("filmList", filmList);
        return "magazine";
    }

    /**
     * 搜索结果展示页面
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "search")
    public String cinema(@RequestParam(value = "key", required = true) String key,
                         @RequestParam(value = "size", required = false, defaultValue = "12") int pageSize,
                         @RequestParam(value = "no", required = false, defaultValue = "1") int pageNo,
                         ModelMap model) {

        PageResults<Film> filmList = filmService.search(key, pageNo, pageSize);

        model.addAttribute("filmList", filmList.getResults());
        return "search";
    }

    /**
     * single页面进入详情页
     * @param filmId
     * @param name
     * @param title
     * @param content
     * @return
     */
    @RequestMapping("single_save_comment")
    @ResponseBody
    public UnifiedResponse saveComment4Single(
                                     @RequestParam(value = "film_id", required = true) long filmId,
                                     @RequestParam(value = "name", required = true) String name,
                                     @RequestParam(value = "title", required = true) String title,
                                     @RequestParam(value = "content", required = true) String content){

        UnifiedResponse unifiedResponse = new UnifiedResponse();

        if(filmId == 0 || StringUtils.isBlank(name) || StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
            unifiedResponse.setMessage("参数错误，请重试!");
            unifiedResponse.setStatus(UnifiedResponseCode.RC_ERROR);
//            return "single?id=" + filmId + "&error=参数错误";
            return unifiedResponse;
        }

        Comment comment = new Comment();
        comment.setFilm_id(filmId);
        comment.setName(name);
        comment.setTitle(title);
        comment.setCreateTime(new Date());
        comment.setStatus(10);
        comment.setContent(content);
        commentService.save(comment);

        unifiedResponse.setStatus(UnifiedResponseCode.RC_SUCC);
        unifiedResponse.setMessage("保存成功");
//        return "redirect:/single?id=" + filmId;
        return unifiedResponse;
    }

    /**
     * singlepage页面进入详情页
     * @param filmId
     * @param name
     * @param title
     * @param content
     * @return
     */
    @RequestMapping("singlepage_save_comment")
    @ResponseBody
    public UnifiedResponse saveComment4SinglePage(
            @RequestParam(value = "film_id", required = true) long filmId,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "content", required = true) String content){

        UnifiedResponse unifiedResponse = new UnifiedResponse();

        if(filmId == 0 || StringUtils.isBlank(name) || StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
            unifiedResponse.setMessage("参数错误，请重试!");
            unifiedResponse.setStatus(UnifiedResponseCode.RC_ERROR);
            return unifiedResponse;
        }

        Comment comment = new Comment();
        comment.setFilm_id(filmId);
        comment.setName(name);
        comment.setTitle(title);
        comment.setCreateTime(new Date());
        comment.setStatus(10);
        comment.setContent(content);
        commentService.save(comment);

        unifiedResponse.setStatus(UnifiedResponseCode.RC_SUCC);
        unifiedResponse.setMessage("保存成功");
        return unifiedResponse;
//        return "redirect:/singlepage?id=" + filmId;
    }

    @RequestMapping("json")
    @ResponseBody
    public List<User> json(){
        return userService.getAllUsernames();
    }

}