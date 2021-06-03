package com.pjq.controller;

import com.pjq.pojo.Content;
import com.pjq.service.SpiderTimer;
import com.pjq.service.WeChatPush;
import org.quartz.JobExecutionException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author pjq
 */
@CrossOrigin
@RestController
public class SpiderController {


//    private WXPageProcessor wxPageProcessor;
//    public static final String url="https://arpost.co/category/augmented-reality/";
    @Resource
    public SpiderTimer spiderTimer;

    @Resource
    public WeChatPush weChatPush;

    @RequestMapping(value = "/start",method = RequestMethod.GET)
    public void spiderStart() throws JobExecutionException {
//        Spider.create(wxPageProcessor).setDownloader(new httpClientDownloader()).addUrl(url).thread(5).run();
        spiderTimer.SpiderTimer();
    }


    @RequestMapping(value = "/sendPost",method = RequestMethod.GET)
    public ArrayList<Content> sendMessage(){
        ArrayList<Content> contents=new ArrayList<>();
        contents=weChatPush.sendPost();
        return contents;
    }
}
