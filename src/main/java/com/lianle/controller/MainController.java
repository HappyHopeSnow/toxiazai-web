package com.lianle.controller;

import com.lianle.common.PageResults;
import com.lianle.entity.Film;
import com.lianle.entity.User;
import com.lianle.service.FilmService;
import com.lianle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lianle on 2/13 0013.
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    FilmService filmService;

    /**
     * 首页
     * @param start
     * @param size
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "index")
    public String home(@RequestParam(value = "start", required = false, defaultValue = "1") int start,
                       @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                       ModelMap model) {

        //首页分页数据(8个)
        PageResults<Film> resultList = filmService.queryByPage(start, size);
        List<Film> filmList = resultList.getResults();

        //推荐列表横幅(10个)
        List<Film> recommendFilms = resultList.getResults();

        //排行榜v
        List<Film> arrayFilms = resultList.getResults();

        //最新上映v
        List<Film> newFilms = resultList.getResults();

        //最热电影v
        List<Film> hotFilms = resultList.getResults();

        //猜你喜欢v
        List<Film> loveFilms = resultList.getResults();

        //归档分类-暂时不做

        model.addAttribute("filmList", filmList);
        model.addAttribute("recommendFilms", recommendFilms);
        model.addAttribute("arrayFilms", arrayFilms);
        model.addAttribute("newFilms", newFilms);
        model.addAttribute("hotFilms", hotFilms);
        model.addAttribute("loveFilms", loveFilms);
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

        PageResults<Film> resultList = filmService.queryByPage(1, 8);

        //排行榜v
        List<Film> arrayFilms = resultList.getResults();

        //最新上映v
        List<Film> newFilms = resultList.getResults();

        //最热电影v
        List<Film> hotFilms =resultList.getResults();

        //猜你喜欢v
        List<Film> loveFilms = resultList.getResults();

        model.addAttribute("arrayFilms", arrayFilms);
        model.addAttribute("newFilms", newFilms);
        model.addAttribute("hotFilms", hotFilms);
        model.addAttribute("loveFilms", loveFilms);
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

    @RequestMapping("")
    public String home(){
//        List<User> us = new ArrayList<User>();
//        User u = new User();
//        u.setUserName("MarK");
//        us.add(u);
//        u = new User();
//        u.setUserName("Fawofolo");
//        us.add(u);
//        userService.saveUsers(us);
        return "index";
    }

    @RequestMapping("get")
    @ResponseBody
    public User saveUser(@RequestParam("id") Long id, ModelMap model){
        User user = userService.queryById(id);

        return user;
    }

    @RequestMapping("json")
    @ResponseBody
    public List<User> json(){
        return userService.getAllUsernames();
    }

    /******************************************以下尚未启用*****************************************/

    @RequestMapping(method = RequestMethod.GET, value = "archives")
    public String archives(ModelMap model) {
        return "archives";
    }

    @RequestMapping(method = RequestMethod.GET, value = "cinema")
    public String cinema(ModelMap model) {
        model.addAttribute("message", "Hello world!aa");
        return "cinema";
    }

    @RequestMapping(method = RequestMethod.GET, value = "magazine")
    public String magazine(ModelMap model) {
        model.addAttribute("message", "Hello world!aa");
        return "magazine";
    }

}