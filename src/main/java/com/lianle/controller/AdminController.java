package com.lianle.controller;

import com.lianle.common.PageResults;
import com.lianle.entity.*;
import com.lianle.service.*;
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
import java.util.List;

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

    @Autowired
    AdviceService adviceService;

    @Autowired
    FilmService filmService;

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
            indexConfig.setRecommend_ids("");
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
            @RequestParam(value = "recommendIds", required = true) String recommendIds,
            @RequestParam(value = "arrayIds", required = true) String arrayIds,
            @RequestParam(value = "newIds", required = true) String newIds,
            @RequestParam(value = "hotIds", required = true) String hotIds,
            @RequestParam(value = "loveIds", required = true) String loveIds){

        UnifiedResponse unifiedResponse = new UnifiedResponse();

        IndexConfig indexConfig;
        if (id == 0) {
            //第一次配置
            indexConfig = new IndexConfig();
            indexConfig.setRecommend_ids(recommendIds);
            indexConfig.setArray_ids(arrayIds);
            indexConfig.setNew_ids(newIds);
            indexConfig.setHot_ids(hotIds);
            indexConfig.setLove_ids(loveIds);
            indexConfig.setCreateTime(new Date());
            indexConfigService.save(indexConfig);
            unifiedResponse.setMessage("保存成功");
        }else {
            indexConfig = indexConfigService.queryById(id);
            indexConfig.setRecommend_ids(recommendIds);
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

    /**
     * 查看建议的url
     * @param pageSize
     * @param pageNo
     * @param model
     * @return
     */
    @RequestMapping("advice")
    @ResponseBody
    public UnifiedResponse json(@RequestParam(value = "size", required = false, defaultValue = "10") int pageSize,
                           @RequestParam(value = "no", required = false, defaultValue = "1") int pageNo,
                           ModelMap model) {

        UnifiedResponse unifiedResponse = new UnifiedResponse();
        PageResults<Advice> resultList = adviceService.queryByPage(pageNo, pageSize);
        unifiedResponse.setAttachment(resultList);
        unifiedResponse.setStatus(UnifiedResponseCode.RC_SUCC);
        unifiedResponse.setMessage("查询成功！");
        return unifiedResponse;
    }

    /**
     * 抓取失败，进行容错
     * @return
     */
    @RequestMapping("check")
    @ResponseBody
    public UnifiedResponse check() {
        LOGGER.info("Start check the dataBase[" + new Date() + "]");
        Long startTime = System.currentTimeMillis();
        //从数据库中读取上次抓取的最大的pid
        UnifiedResponse unifiedResponse = new UnifiedResponse();

        //select * from film order by id desc limit 1;
        Film film = filmService.queryByMaxId();

        //select * from curl_log order by create_time desc limit 1;
        CurlLog curlLog = curlLogService.queryLast();

        if (film != null && curlLog != null && Long.parseLong(film.getParent_id()) > curlLog.getEnd_id()) {
            //有异常需要更新
            //update curl_log set end_id = film.getId() where id = curl_log.getId();
            curlLog.setEnd_id(Long.valueOf(film.getParent_id()));
            curlLogService.update(curlLog);
            unifiedResponse.setAttachment(curlLog);
        }

        LOGGER.info("End check the dataBase,and it costs [" + (System.currentTimeMillis() - startTime) + "]毫秒!");
        return unifiedResponse;
    }


}
