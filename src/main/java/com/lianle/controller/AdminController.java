package com.lianle.controller;

import com.lianle.entity.CurlLog;
import com.lianle.entity.UnifiedResponse;
import com.lianle.service.CurlLogService;
import com.lianle.service.CurlManagerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

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

    //不抓取的链接
    private static final String notCurlParentIds = "[48]";

    @Autowired
    CurlManagerService curlManagerService;

    @Autowired
    CurlLogService curlLogService;

    @RequestMapping(method = RequestMethod.GET, value = "curl")
    @ResponseBody
    public UnifiedResponse curl(@RequestParam("id") String parentId){
        return curlManagerService.curl(parentId);
    }


    @RequestMapping("curl_job")
    @ResponseBody
    public UnifiedResponse curlJob() {
        LOGGER.info("Start The Curl Job...,and now is [" + new Date() + "]");
        Long startTime = System.currentTimeMillis();
        //从数据库中读取上次抓取的最大的pid
        CurlLog oldCurlLog = curlLogService.queryLast();
        CurlLog newCurlLog = new CurlLog();

        Long startId;
        if (oldCurlLog == null) {
            //系统第一次
            startId = Long.parseLong("19");//第一个电影从id=19开始
            newCurlLog.setStart_id(startId);
        }else {
            newCurlLog.setStart_id(oldCurlLog.getEnd_id());
            startId = oldCurlLog.getEnd_id() + 1;
        }
        //每天抓取20个
        UnifiedResponse unifiedResponse = new UnifiedResponse();
        for (int i = 0; i < 5; i++) {
            startId++;
            //抓取并返回下次的Pid;
            if (notCurlParentIds.contains(startId + "")) {
                continue;
            }
            unifiedResponse = curlManagerService.curl(startId + "");
            if (unifiedResponse.getStatus() != 200) {
                //抓失败，写入最大数值，等待明天抓取，跳出循环
                break;
            }
        }
        newCurlLog.setEnd_id(startId);
        newCurlLog.setCreateTime(new Date());
        curlLogService.save(newCurlLog);

        LOGGER.info("End The Curl Job...,and it costs [" + (System.currentTimeMillis() - startTime) + "]毫秒!");
        return unifiedResponse;
    }
}
