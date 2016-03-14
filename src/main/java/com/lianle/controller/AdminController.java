package com.lianle.controller;

import com.lianle.entity.*;
import com.lianle.service.*;
import com.lianle.utils.JUtils.base.RegexUtils;
import com.lianle.utils.JUtils.file.FileUtils;
import com.lianle.utils.http.exception.HttpProcessException;
import com.lianle.utils.http.httpclient.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: <br>
 *
 * @author <a href=mailto:lianle1@jd.com>连乐</a>
 * @date 2016/2/18 14:32
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);

    private static final String saveResourcePath;
    static {
        String osName = System.getProperty("os.name");
        LOGGER.info("System OS Name is[" + osName + "]");
        if (osName.startsWith("Win")) {
            //windows操作系统
            saveResourcePath = "d:\\\\";
        }else {
            saveResourcePath = "/opt/resource/";
        }
    }

    @Autowired
    UserService userService;

    @Autowired
    FilmService filmService;

    @Autowired
    FormatService formatService;

    @Autowired
    ScreenService screenService;

    @Autowired
    CountryService countryService;

    @Autowired
    ClassTypeService classTypeService;

    @Autowired
    LanguageService languageService;

    @Autowired
    PerformerService performerService;

    @Autowired
    FilmClassRelService filmClassRelService;

    @Autowired
    FilmCountryRelService filmCountryRelService;

    @Autowired
    FilmLanguageRelService filmLanguageRelService;

    @Autowired
    FilmPerformerRelService filmPerformerRelService;

    @Autowired
    CurlManagerService curlManagerService;

    @RequestMapping(method = RequestMethod.GET, value = "curl")
    @ResponseBody
    public UnifiedResponse curl(@RequestParam("id") String parentId){
        return curlManagerService.curl(parentId);
    }
}
