package com.tgb.ccl.http;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lianle.utils.JUtils.base.RegexUtils;
import com.lianle.utils.JUtils.file.FileUtils;
import com.lianle.utils.http.common.HttpHeader;
import com.lianle.utils.http.exception.HttpProcessException;
import com.lianle.utils.http.httpclient.HttpClientUtil;
import com.lianle.utils.http.httpclient.builder.HCB;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;


/** 
 * 
 * @author arron
 * @date 2015年11月1日 下午2:23:18 
 * @version 1.0 
 */
public class HttpClientTest {

	public static void testOne() throws HttpProcessException {
		
		System.out.println("--------简单方式调用（默认post）--------");
		String url = "http://tool.oschina.net/";
		//简单调用
		String resp = HttpClientUtil.send(url);
		System.out.println("请求结果内容长度："+ resp.length());
		
		System.out.println("\n#################################\n");
		
		System.out.println("--------加入header设置--------");
		url="http://blog.csdn.net/xiaoxian8023";
		//设置header信息
		Header[] headers= HttpHeader.custom().userAgent("Mozilla/5.0").build();
		//执行请求
		resp = HttpClientUtil.send(url, headers);
		System.out.println("请求结果内容长度："+ resp.length());

		System.out.println("\n#################################\n");
		
		System.out.println("--------代理设置（绕过证书验证）-------");
		url="https://www.facebook.com/";
		HttpClient client= HCB.custom().timeout(10000).proxy("127.0.0.1", 8087).ssl().build();//采用默认方式（绕过证书验证）
		//执行请求
		resp = HttpClientUtil.send(client,url);
		System.out.println("请求结果内容长度："+ resp.length());

		System.out.println("\n#################################\n");

		System.out.println("--------代理设置（自签名证书验证）+header+get方式-------");
		url = "https://sso.tgb.com:8443/cas/login";
		client= HCB.custom().timeout(10000).ssl("D:\\keys\\wsriakey","tomcat").build();
		headers=HttpHeader.custom().keepAlive("false").connection("close").contentType(HttpHeader.Headers.APP_FORM_URLENCODED).build();
		//执行请求
		resp = HttpClientUtil.send(client, url, HttpClientUtil.HttpMethods.GET, headers);
		System.out.println("请求结果内容长度："+ resp.length());

		System.out.println("\n#################################\n");
	}
	
	
	
	public static void testMutilTask(){
		// URL列表数组
		String[] urls = {
				"http://blog.csdn.net/xiaoxian8023/article/details/49883113",
				"http://blog.csdn.net/xiaoxian8023/article/details/49909359",
				"http://blog.csdn.net/xiaoxian8023/article/details/49910127",
				"http://blog.csdn.net/xiaoxian8023/article/details/49910885",
				
//				"http://blog.csdn.net/xiaoxian8023/article/details/49862725",
//				"http://blog.csdn.net/xiaoxian8023/article/details/49834643",
//				"http://blog.csdn.net/xiaoxian8023/article/details/49834615",
//				"http://blog.csdn.net/xiaoxian8023/article/details/49834589",
//				"http://blog.csdn.net/xiaoxian8023/article/details/49785417",
//				
//				"http://blog.csdn.net/xiaoxian8023/article/details/48679609",
//				"http://blog.csdn.net/xiaoxian8023/article/details/48681987",
//				"http://blog.csdn.net/xiaoxian8023/article/details/48710653",
//				"http://blog.csdn.net/xiaoxian8023/article/details/48729479",
//				"http://blog.csdn.net/xiaoxian8023/article/details/48733249",
//
//				"http://blog.csdn.net/xiaoxian8023/article/details/48806871",
//				"http://blog.csdn.net/xiaoxian8023/article/details/48826857",
//				"http://blog.csdn.net/xiaoxian8023/article/details/49663643",
//				"http://blog.csdn.net/xiaoxian8023/article/details/49619777",
//				"http://blog.csdn.net/xiaoxian8023/article/details/47335659",
//
//				"http://blog.csdn.net/xiaoxian8023/article/details/47301245",
//				"http://blog.csdn.net/xiaoxian8023/article/details/47057573",
//				"http://blog.csdn.net/xiaoxian8023/article/details/45601347",
//				"http://blog.csdn.net/xiaoxian8023/article/details/45569441",
//				"http://blog.csdn.net/xiaoxian8023/article/details/43312929", 
				};
		
		// 设置header信息
		Header[] headers = HttpHeader.custom().userAgent("Mozilla/5.0").from("http://blog.csdn.net/newest.html").build();
		HttpClient client= HCB.custom().timeout(10000).build();
		
		 long start = System.currentTimeMillis();        
	        try {
	            int pagecount = urls.length;
	            ExecutorService executors = Executors.newFixedThreadPool(pagecount);
	            CountDownLatch countDownLatch = new CountDownLatch(pagecount*50);         
	            for(int i = 0; i< pagecount*50;i++){
	                //启动线程抓取
	                executors.execute(new GetRunnable(urls[i%pagecount], headers, countDownLatch).setClient(client));
	            }
	            countDownLatch.await();
	            executors.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } finally {
	            System.out.println("线程" + Thread.currentThread().getName() + ", 所有线程已完成，开始进入下一步！");
	        }
	         
	        long end = System.currentTimeMillis();
	        System.out.println("总耗时（毫秒）： -> " + (end - start));
	        //(7715+7705+7616)/3= 23 036/3= 7 678.66---150=51.2
	        //(9564+8250+8038+7604+8401)/5=41 857/5=8 371.4--150
	        //(9803+8244+8188+8378+8188)/5=42 801/5= 8 560.2---150
	}
	
	 static class GetRunnable implements Runnable {
	        private CountDownLatch countDownLatch;
	        private String url;
	        private Header[] headers;
	        private HttpClient client = null;
	        
	        public GetRunnable setClient(HttpClient client){
	        	this.client = client;
	        	return this;
	        }

	        public GetRunnable(String url, Header[] headers,CountDownLatch countDownLatch){
	        	this.url = url;
	        	this.headers = headers;
	            this.countDownLatch = countDownLatch;
	        }
	        @Override
	        public void run() {
	            try {
	            	String response = null;
	            	if(client!=null){
	            		response = HttpClientUtil.send(client, url, headers);
	            	}else{
	            		response =  HttpClientUtil.send(url, headers);
	            	}
	            	System.out.println(Thread.currentThread().getName()+"--获取内容长度："+response.length());
	            } catch (HttpProcessException e) {
					e.printStackTrace();
				} finally{
	                countDownLatch.countDown();
	            }
	        }
	    }  
	
	public static void main(String[] args) throws Exception {
//		testOne();
//		testMutilTask();
		testTwo();
	}

	private static void testTwo() throws Exception {
		System.out.println("--------简单方式调用（默认post）--------");

		//1.&&&&down下文件

		String url = "http://5280bt.com/3761.html";
		//简单调用
		String resp = HttpClientUtil.send(url);

		System.out.println("\n#################################\n");

		//正则表达式，获取编号
		String idRegex = "[1-9]\\d*\\.+";
		String fileName = RegexUtils.get(idRegex, url);
		fileName = fileName.substring(0, fileName.length() - 1);

//2.&&&&保存文件，包括名称
		fileName = "d:\\\\" + fileName + ".html";
		//保存字符串到文件中
		FileUtils.createNewFile(fileName, resp);
		System.out.println("After save file to D:");

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