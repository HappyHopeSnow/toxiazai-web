package com.lianle.controller;

import com.lianle.entity.CurlLog;
import com.lianle.entity.IndexConfig;
import com.lianle.entity.UnifiedResponse;
import com.lianle.entity.UnifiedResponseCode;
import com.lianle.service.CurlLogService;
import com.lianle.service.CurlManagerService;
import com.lianle.service.IndexConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @Autowired
    IndexConfigService indexConfigService;

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

    /**
     * 进入首页配置页面
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "index_config")
    public String indexConfig(ModelMap model) {
        IndexConfig indexConfig = indexConfigService.queryLast();
        if (indexConfig == null) {
            indexConfig = new IndexConfig();
            indexConfig.setId(0l);
            indexConfig.setHot_ids("");
            indexConfig.setLove_ids("");
            indexConfig.setNew_ids("");
            indexConfig.setArray_ids("");
        }
        model.addAttribute("indexConfig", indexConfig);
        return "admin/index_config";
    }

    /**
     * 保存首页配置ids信息
     * @param arrayIds
     * @param newIds
     * @param hotIds
     * @param loveIds
     * @return
     */
    @RequestMapping("save_index_config")
    @ResponseBody
    public UnifiedResponse saveAdvice(
            @RequestParam(value = "id", required = false, defaultValue = "0") long id,
            @RequestParam(value = "arrayIds", required = true) String arrayIds,
            @RequestParam(value = "newIds", required = true) String newIds,
            @RequestParam(value = "hotIds", required = true) String hotIds,
            @RequestParam(value = "loveIds", required = true) String loveIds){

        UnifiedResponse unifiedResponse = new UnifiedResponse();

        IndexConfig indexConfig;
        if (id == 0) {
            //第一次配置
            indexConfig = new IndexConfig();
            indexConfig.setArray_ids(arrayIds);
            indexConfig.setNew_ids(newIds);
            indexConfig.setHot_ids(hotIds);
            indexConfig.setLove_ids(loveIds);
            indexConfig.setCreateTime(new Date());
            indexConfigService.save(indexConfig);
            unifiedResponse.setMessage("保存成功");
        }else {
            indexConfig = indexConfigService.queryById(id);
            indexConfig.setArray_ids(arrayIds);
            indexConfig.setNew_ids(newIds);
            indexConfig.setHot_ids(hotIds);
            indexConfig.setLove_ids(loveIds);
            indexConfig.setCreateTime(new Date());
            indexConfigService.update(indexConfig);
            unifiedResponse.setMessage("更新成功");
        }

        unifiedResponse.setStatus(UnifiedResponseCode.RC_SUCC);
        return unifiedResponse;
    }
}
