package com.lianle.controller;

import com.lianle.entity.Film;
import com.lianle.entity.User;
import com.lianle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(method = RequestMethod.GET, value = "index")
    public String home(ModelMap model) {
        model.addAttribute("message", "Hello world!aa");
        return "index";
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

    @RequestMapping("save")
    public String saveUser(){
        User user = new User();
        user.setId(2);
        user.setUserName("haha");
        userService.save(user);
        return "home";
    }

    @RequestMapping("json")
    @ResponseBody
    public List<User> json(){
        return userService.getAllUsernames();
    }

    /************************************抓取测试start*************************************************/
    @RequestMapping("curl")
    @ResponseBody
    public Film curl(){
        Film film = new Film();

        return film;
    }
    /************************************抓取测试end*************************************************/

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

    @RequestMapping(method = RequestMethod.GET, value = "contact")
    public String contact(ModelMap model) {
        model.addAttribute("message", "Hello world!aa");
        return "contact";
    }

    @RequestMapping(method = RequestMethod.GET, value = "magazine")
    public String magazine(ModelMap model) {
        model.addAttribute("message", "Hello world!aa");
        return "magazine";
    }

    @RequestMapping(method = RequestMethod.GET, value = "single")
    public String single(ModelMap model) {
        model.addAttribute("message", "Hello world!aa");
        return "single";
    }

    @RequestMapping(method = RequestMethod.GET, value = "singlepage")
    public String singlepage(ModelMap model) {
        model.addAttribute("message", "Hello world!aa");
        return "singlepage";
    }
}