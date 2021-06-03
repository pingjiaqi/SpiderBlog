package com.pjq.service;

import com.pjq.dao.WebsiteDao;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


/**
 * @program:
 * @description: 定时启动爬虫
 * @author:
 * @create:
 **/
@Component
@EnableScheduling
public class SpiderTimer {

    @Resource
    private TestPageAnalysis testPageAnalysis;

    @Resource
    private ARPostPageAnalysis arPostPageAnalysis;

    @Resource
    private WebsiteDao websiteMapper;

    @Scheduled(fixedDelay = 1000*60*20)
    public void SpiderTimer() {

//        int j=3;
//        while(j>0){
//            j--;
//            test.t1(j);
//        }

//        List<Website> websiteArrayList=new ArrayList<>();
//        websiteArrayList=websiteMapper.selectAllWebsite();
//
//        for(Website w:websiteArrayList){
//            System.out.println(w.getWebsite_name()+"start"+new Date());
//            testPageAnalysis.start(w.getUrl());
//        }
//        testPageAnalysis.start("https://arpost.co/category/augmented-reality/");
//
        System.out.println("\n--------------\n" +
                "Spider start" +
                "\n开始，当前时间： " +
                new Date() +
                "\n--------------\n");
//        if (!isStart) {
//            isStart = true;
            arPostPageAnalysis.start();
//        }
    }
}
