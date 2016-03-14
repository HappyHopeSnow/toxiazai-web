package com.lianle.job;

import com.lianle.entity.UnifiedResponse;
import com.lianle.service.CurlManagerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lianle on 2016/3/14.
 */
@Component("taskJob")
public class TaskJob {

    private static final Logger LOGGER = LogManager.getLogger(TaskJob.class);

    /**
     "0 0 12 * * ?"    每天中午十二点触发
     "0 15 10 ? * *"    每天早上10：15触发
     "0 15 10 * * ?"    每天早上10：15触发
     "0 15 10 * * ? *"    每天早上10：15触发
     "0 15 10 * * ? 2005"    2005年的每天早上10：15触发
     "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发
     "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发
     "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
     "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发
     "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发
     "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发

     */

    @Autowired
    CurlManagerService curlManagerService;

    /**
     * 抓取任务
     * 每天晚上1:00
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void curlJob() {
        LOGGER.info("Start The Curl Job...,and now is [" + new Date() + "]");
        Long startTime = System.currentTimeMillis();
        //从数据库中读取上次抓取的最大的pid
        Long lastPid = 100l;
        //每天抓取20个
        UnifiedResponse unifiedResponse = null;
        for (int i = 0; i < 20; i++) {
            lastPid = lastPid + i;
            //抓取并返回下次的Pid;
            unifiedResponse = curlManagerService.curl(lastPid + "");
            if (unifiedResponse.getStatus() != 200) {
                //抓失败，写入最大数值，等待明天抓取，跳出循环
//                curlLogService.save();
                break;
            }
        }
        LOGGER.info("End The Curl Job...,and it costs [" + (System.currentTimeMillis() - startTime) + "]毫秒!");
    }
}