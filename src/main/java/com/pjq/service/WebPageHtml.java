package com.pjq.service;

import com.pjq.dao.WebHtmlDao;
import com.pjq.dao.WebPageNumberHtmlDao;
import com.pjq.dao.WebsiteDao;
import com.pjq.pojo.WebHtml;
import com.pjq.pojo.WebPageNumberHtml;
import com.pjq.pojo.Website;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: WX
 * @description:
 * @author:
 * @create: 2019-11-28 15:58
 **/
@Service
public class WebPageHtml {

    @Resource
    private WebHtmlDao webHtmlMapper;

    @Resource
    private WebsiteDao websiteMapper;

    @Resource
    private WebPageNumberHtmlDao webPageNumberHtmlMapper;


    public WebHtml showHtml(String webUrl){
        WebHtml webHtml=new WebHtml();

        webUrl=webUrl.substring(0,15);

        Website website=websiteMapper.selectWebsite(webUrl+"%");

        webHtml=webHtmlMapper.selectAllHtml(website.getId());

        return webHtml;
    }

    public WebPageNumberHtml showPagesNumberHtml(String webUrl){
        WebPageNumberHtml webPageNumberHtml =new WebPageNumberHtml();

        webUrl=webUrl.substring(0,15);

        Website website=websiteMapper.selectWebsite(webUrl+"%");

        webPageNumberHtml =webPageNumberHtmlMapper.selectPageHtml(website.getId());

        return webPageNumberHtml;
    }





}
