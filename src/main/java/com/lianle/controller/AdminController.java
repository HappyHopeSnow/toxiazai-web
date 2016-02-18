package com.lianle.controller;

import com.lianle.utils.JUtils.base.RegexUtils;
import com.lianle.utils.JUtils.file.FileUtils;
import com.lianle.utils.http.exception.HttpProcessException;
import com.lianle.utils.http.httpclient.HttpClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
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

    @RequestMapping(method = RequestMethod.GET, value = "")
    public void doWork(){
        System.out.println("--------简单方式调用（默认post）--------");
        //调用链接，保存文件
        String url = "http://5280bt.com/3761.html";
        //访问url
        String result = postUrl(url);

        //保存成文件
        String fileName = "3761";
        saveFile(result, fileName);

        /**进行存储操作**/
        //正则匹配,并保存电影基本信息
        String filmName = mathFilmName(result);

        //5.&&&&根据名称进行分类
        //todo

        //正则匹配三张图片的url
        List<String> picUrlList = matchPic(result);
        //下载这三张图片并保存
        //todo

        //获取中间部分,并进行配置相关属性
        String middleContent = getMiddleConte(result);


        //获取种子链接
        String torrentUrl = getTorrentUrl(result);

        //下载种子文件
        String torrentName = "TODO";
        downLoadTorrent(torrentUrl, torrentName);

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

    private String getMiddleConte(String result) {
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
        return middleResult;
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
        fileName += "d:\\\\" + fileName + ".html";
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
