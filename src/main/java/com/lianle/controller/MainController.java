package com.lianle.controller;

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
        return "home";
    }

    @RequestMapping("")
    public String home(){
        List<User> us = new ArrayList<User>();
        User u = new User();
        u.setUserName("MarK");
        us.add(u);
        u = new User();
        u.setUserName("Fawofolo");
        us.add(u);
        userService.saveUsers(us);
        return "home";
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
}