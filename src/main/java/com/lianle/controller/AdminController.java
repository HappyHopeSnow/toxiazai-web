package com.lianle.controller;

import com.lianle.entity.*;
import com.lianle.service.*;
import com.lianle.utils.JUtils.base.RegexUtils;
import com.lianle.utils.JUtils.file.FileUtils;
import com.lianle.utils.http.exception.HttpProcessException;
import com.lianle.utils.http.httpclient.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    @RequestMapping(method = RequestMethod.GET, value = "")
    @ResponseBody
    public Film doWork(){
        Film film = new Film();

        //上传者
        User user = userService.getAdmin();
        film.setUid(user.getId());

        System.out.println("--------简单方式调用（默认post）--------");
        //调用链接，保存文件
        String url = "http://5280bt.com/3761.html";
        LOGGER.info("The URL is [" + url + "]");
        //访问url
        String result = postUrl(url);

        //保存成文件
        String fileName = "3761";
//todo open        saveFile(result, fileName);

        /**进行存储操作**/
        //正则匹配,并保存电影基本信息
        String filmName = mathFilmName(result);
        LOGGER.info("********* Film title is[" + fileName + "]");
        filmName = filmName.split(" ")[8];
        String[] filmResult = filmName.split("]");

        //根据标题名称进行分类
        filmName = filmResult[0].substring(1, filmResult[0].length());
        String formatName = filmResult[1].substring(1, filmResult[1].length());
        String size = filmResult[2].substring(1, filmResult[2].length());
        String downModel = filmResult[3].substring(1, filmResult[3].length());
        String captionsType = filmResult[4].substring(1, filmResult[4].length());

        //cover_id

        //format_id
        processFormat(film, formatName);

        film.setName(filmName);
        film.setSize(size);
        film.setScore(6.1f);
        film.setDown_model(downModel);
        film.setCaptions_type(captionsType);

        //正则匹配三张图片的url
        List<String> picUrlList = matchPic(result);
        //下载这三张图片并保存
        //todo

        //获取中间部分,并进行配置相关属性
        processMiddleContent(film, result);

        Date now = new Date();
        film.setCreateTime(now);
        film.setModifyTime(now);

        //关键字=名字+导演+演员+类型+简介
        buildKeyWrod(film);

        filmService.save(film);

        //获取种子链接
        String torrentUrl = getTorrentUrl(result);

        //下载种子文件
        String torrentName = "TODO";
        downLoadTorrent(torrentUrl, torrentName);

        return film;

    }

    private void buildKeyWrod(Film film) {
        //关键字=名字+导演+演员+类型+简介
        StringBuilder keyWord = new StringBuilder();
        keyWord.append(film.getName());
        keyWord.append(film.getOther_name());
        keyWord.append(film.getDirector());
        keyWord.append(film.getPerformer());
        keyWord.append(film.getClass_name());
        keyWord.append(film.getDescription());

        film.setKey_word(keyWord.toString());
    }

    /**
     * 处理格式
     * @param film
     * @param formatName
     */
    private void processFormat(Film film, String formatName) {
        Format format = formatService.queryByName(formatName);
        if (format == null) {
            //new
            format = new Format();
            format.setFormat_name(formatName);
            formatService.save(format);
        }

        film.setFormat(formatName);
        film.setFormat_id(format.getId());

    }


    private void downLoadTorrent(String torrentUrl, String torrentName) {
        //9.&&&&下载链接种子
        FileUtils.downloadFile(torrentUrl, "d:\\\\" + torrentName);
    }

    private String getTorrentUrl(String result) {
        String torrentRegex = "[a-zA-z]+://[^\\s]*\\.torrent";
        String torrentUrl = RegexUtils.get(torrentRegex, result);
        System.out.println("the torrent url is \n" + torrentUrl);
        return torrentUrl;
    }

    private void processMiddleContent(Film film, String result) {
        //7.&&&&获取中间部分
        String middleReg = "<p>时间.*。</p>";
        String middleResult = RegexUtils.get(middleReg, result);

        //<p>分割
        String[] pList = middleResult.split("<p>");
        System.out.println("plist is \n" + pList);

        String[] bodyResult = pList[1].split("<br />");

        //获取时间等相关属性
        //0-时间
        String time = bodyResult[0].substring(3, bodyResult[0].length());
        processScreenYear(film, time);

        //1-国别
        String country =  getBodyContentByIndex(bodyResult, 1);
        processCountry(film, country);

        //2-分类
        String className = getBodyContentByIndex(bodyResult, 2);
        processClass(film, className);

        //3-语言
        String language = getBodyContentByIndex(bodyResult, 3);
        processLanguage(film, language);

        //4-字幕


        //5-片长
        String length = getBodyContentByIndex(bodyResult, 5);
        film.setLength(length);

        //6-视频尺寸
        String movieSize = bodyResult[6].substring(5, bodyResult[6].length());
        film.setMovie_size(movieSize);

        //7-导演
        String director = getBodyContentByIndex(bodyResult, 7);
        film.setDirector(director);

        //8-主演
        List<String> performerList = getAllPerfomer(bodyResult);
        processPerformer(performerList);

        //添加主演字段
        StringBuilder performerBuilder = new StringBuilder();
        for(int i = 0; i < performerList.size(); i++) {
            performerBuilder.append(performerList.get(i));
        }
        film.setPerformer(performerBuilder.toString());

        //19-剧情介绍

        //获取简介
        String content = pList[2];
        content = content.substring(0, content.length() - 4);
        film.setDescription(content);

    }

    private void processPerformer(List<String> performerList) {
        Performer performer;
        for (int i = 0; i < performerList.size(); i++) {
            performer = performerService.queryByName(performerList.get(i));
            if (performer == null) {
                //new
                performer = new Performer();
                performer.setName(performerList.get(i));
                performerService.save(performer);
            }
        }
    }

    private List<String> getAllPerfomer(String[] bodyResult) {
        List<String> performers = new ArrayList<String>();
        String firstPerformer = getBodyContentByIndex(bodyResult, 8);
        performers.add(firstPerformer);
        for(int i = 9; i < bodyResult.length - 1; i++) {
            performers.add(bodyResult[i]);
        }
        return performers;
    }

    private void processLanguage(Film film, String languageName) {
        Language language = languageService.queryByName(languageName);
        if (language == null) {
            //new
            language = new Language();
            language.setLanguage(languageName);
            languageService.save(language);
        }

        film.setLanguage_id(language.getId());
        film.setLanguage(languageName);
    }

    private void processClass(Film film, String className) {
        ClassType classType = classTypeService.queryByName(className);
        if (classType == null) {
            //new
            classType = new ClassType();
            classType.setClass_name(className);
            classTypeService.save(classType);
        }

        film.setClass_id(classType.getId());
        film.setClass_name(className);
    }

    private void processCountry(Film film, String countryName) {
        Country country = countryService.queryByName(countryName);
        if (country == null) {
            //new
            country = new Country();
            country.setName(countryName);
            countryService.save(country);
        }

        film.setCountry(countryName);
        film.setCountry_id(country.getId());
    }

    /**
     * 获取数组对应的内容
     * @param bodyResult
     * @param index
     * @return
     */
    private String getBodyContentByIndex(String[] bodyResult, int index) {
        return bodyResult[index].substring(4, bodyResult[index].length());
    }

    /**
     * 处理上映年份
     * @param film
     * @param time
     */
    private void processScreenYear(Film film, String time) {
        Screen screen = screenService.queryByName(time);
        if (screen == null) {
            //new
            screen = new Screen();
            screen.setScreen_year(Integer.parseInt(time));
            screenService.save(screen);
        }

        film.setScreen_year(time);
        film.setScreen_date(time);
        film.setScreen_year_id(screen.getId());

    }

    private List<String> matchPic(String result) {
        //6.&&&&匹配三个展示图
		Matcher m = Pattern.compile("src=\"http://.*?\"").matcher(result);
		int pCount = 0;
		List<String> pList = new ArrayList<String>(3);
		while(m.find()){
			String match=m.group();
			//Pattern.CASE_INSENSITIVE忽略'jpg'的大小写
			Matcher k= Pattern.compile("src=\"http://.*?.jpg", Pattern.CASE_INSENSITIVE).matcher(match);
			if(k.find()){
				pList.add(match.substring(5, match.length() -1));
				pCount ++;
				if (pCount == 3) {
					break;
				}
			}
		}
		System.out.println("plist is \n" + pList);
        return pList;
    }

    private String mathFilmName(String result) {
        //4.&&&&匹配电影名字
		String filmNameRegex = "<h1>.*</h1>";
		return RegexUtils.get(filmNameRegex, result);
    }

    private void saveFile(String result, String fileName) {
        fileName = "d:\\\\" + fileName + ".html";
        LOGGER.debug("Begin save the url result to the file path [" + fileName + "]!");
        //保存字符串到文件中
        FileUtils.createNewFile(fileName, result);
        LOGGER.debug("end save the url result to the file path [" + fileName + "]!");
    }


    private String postUrl(String url) {
        LOGGER.debug("start to down file by the link : [" + url + "] !");
        //简单调用
        String resp = null;
        try {
            resp = HttpClientUtil.send(url);
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        LOGGER.debug("end to down file by the link : [" + url + "] !");
        return resp;
    }

    public void process() {

        System.out.println("--------简单方式调用（默认post）--------");

       /* //1.&&&&down下文件

        String url = "http://5280bt.com/3761.html";
        //简单调用
        String resp = null;
        try {
            resp = HttpClientUtil.send(url);
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }

        System.out.println("\n#################################\n");*/
/*
        //正则表达式，获取编号
        String idRegex = "[1-9]\\d*\\.+";
        String fileName = RegexUtils.get(idRegex, url);
        fileName = fileName.substring(0, fileName.length() - 1);*/

//2.&&&&保存文件，包括名称
     /*   fileName = "d:\\\\" + fileName + ".html";
        //保存字符串到文件中
        FileUtils.createNewFile(fileName, resp);
        System.out.println("After save file to D:");*/

//3.&&&&读取文件
        System.out.println("start to read file !");
        String path = "d:\\\\3761.html";
        String result = FileUtils.readFileByLines(path);
        System.out.println("result is\n" +result);


        //4.&&&&匹配电影名字
	/*	String filmNameRegex = "<h1>.*</h1>";
		String fileName = RegexUtils.get(filmNameRegex, result);
		System.out.println("fileName is \n" + fileName);*/

        //5.&&&&根据名称进行分类
        //todo

        //6.&&&&匹配三个展示图
		/*Matcher m = Pattern.compile("src=\"http://.*?\"").matcher(result);
		int pCount = 0;
		List<String> pList = new ArrayList<String>(3);
		while(m.find()){
			String match=m.group();
			//Pattern.CASE_INSENSITIVE忽略'jpg'的大小写
			Matcher k= Pattern.compile("src=\"http://.*?.jpg", Pattern.CASE_INSENSITIVE).matcher(match);
			if(k.find()){
				pList.add(match.substring(5, match.length() -1));
				pCount ++;
				if (pCount == 3) {
					break;
				}
			}
		}
		System.out.println("plist is \n" + pList);*/

        //7.&&&&获取中间部分
        String middleReg = "<p>时间.*。</p>";
        String middleResult = RegexUtils.get(middleReg, result);
        System.out.println("middle is \n" + middleResult);

        //<p>分割
        String[] pList;

        pList = middleResult.split("<p>");
        System.out.println("plist is \n" + pList);

        //获取时间相关属性
        String paramString = pList[1];
        //<br>分割
        String[] brList = paramString.split("<br />");
        System.out.println("br is + \n" + brList);

        //获取简介
        String content = pList[2];
        content = content.substring(0, content.length() - 4);
        System.out.println("content is\n" + content);

        //8.&&&&获取种子链接
        String torrentRegex = "[a-zA-z]+://[^\\s]*\\.torrent";
        String torrentUrl = RegexUtils.get(torrentRegex, result);
        System.out.println("the torrent url is \n" + torrentUrl);

        //获取种子名字
        String[] torrentUrlStr = torrentUrl.split("/");
        String torrentName = torrentUrlStr[torrentUrlStr.length - 1];
        //9.&&&&下载链接种子
        FileUtils.downloadFile(torrentUrl, "d:\\\\" + torrentName);
    }
}
