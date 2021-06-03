package com.pjq.service;

import com.pjq.dao.ContentDao;
import com.pjq.pojo.Content;
import com.pjq.pojo.WebPages;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program:
 * @description: ARPOST网站页面数据
 * @author:
 * @create:
 **/
@Service
//@Async("taskExecutor")
public class ARPostPageAnalysis {

    @Resource
    private ContentDao contentDao;

    @Resource
    private HttpClientResponse httpClientResponse;

    private int pages = 1;

    //下一个页面的url
    private String nextUrl;

    //当前页面的页数
    private int currentPage = 0;

    private boolean isContinue = true;

    private String pageUrl = "https://arpost.co/category/augmented-reality/";


    //html页面解析
    public WebPages page(String webUrl) throws IOException, InterruptedException {
        WebPages url = new WebPages();
        String htmltxt = null;

        //请求页面
        htmltxt = httpClientResponse.httpClientResponse(webUrl);
        Content content = new Content();
        if ((htmltxt == "error" || htmltxt == "time out") && isContinue == true) {
            url.setPages(pages);
            url.setNextUrl(nextUrl);
            url.setCurrentPage(currentPage);
            isContinue = false;
            return url;
        }
        isContinue = true;

        //解析html
        Document document = Jsoup.parse(htmltxt);
        Elements elements = document.select("div[class='blog-listing-el']").select("article[class='post-wrap post-grid post-grid-3']");
        Elements elements1 = document.select("div[class='pagination-number']").select("a[class='page-numbers']");
        for (Element element : elements) {
            String title = element.select("div[class='post-body']").select("a").attr("title");
            int hashcode = title.hashCode();
            String articleUrl = element.select("div[class='post-body']").select("a").attr("href");
            String time = element.select("div[class='post-body']").select("time").attr("datetime");
            String imageUrl = element.select("div[class='post-header']").select("span[class='thumbnail-resize']").select("img").attr("src");
            String t = "T";
            String t1 = "+00:00";
            time = time.replace(t, " ");
            time = time.replace(t1, "");

            content.setTitle(title);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                Date date = simpleDateFormat.parse(time);
                content.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            content.setUrl(articleUrl);
            content.setImage(imageUrl);
            String des = description(articleUrl);
            if (contentDao.selectHashCode(hashcode) == null) {
                contentDao.insertIntoContent(0, title, des, imageUrl, articleUrl, content.getTime(), hashcode, 1);
            }
            Thread.sleep(10000);
        }

        //网站下一页的url
        for (Element element : elements1) {
            if (pages < Integer.parseInt(element.text())) {
                pages = Integer.parseInt(element.text());
            }
        }
        nextUrl = document.select("a[class='next page-numbers']").attr("href");
        currentPage = Integer.parseInt(document.select("div[class='pagination-number']").select("span[class='page-numbers current']").text());
        url.setPages(pages);
        url.setNextUrl(nextUrl);
        url.setCurrentPage(currentPage);
        return url;
    }


    //帖子内容解析
    public String description(String detailUrl) {
        String des = null;
        String htmltxt = null;
        try {
            htmltxt = httpClientResponse.httpClientResponse(detailUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document document = Jsoup.parse(htmltxt);
        Elements elements = document.select("div[class='single-subtitle post-title is-size-3']").select("h3");
        des = elements.text();
        return des;

    }


    //网站爬虫启动
    @Async("taskExecutor")
    public void start() {

        System.out.println("ARPOST Start： " + new Date());

        WebPages url1 = new WebPages();
        int i = 0;
        try {
            for (i = 1; i <= pages; i++) {
                url1 = page(pageUrl);
                System.out.println(pageUrl);
                System.out.println("第" + i + "页");
                pageUrl = url1.getNextUrl();
                pages = url1.getPages();
                Thread.sleep(10000000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("第" + i + "页");
        }
        if (i > 2) {
            pageUrl = "https://arpost.co/category/augmented-reality/page/";
        }

        System.out.println("ARPOST End： " + new Date());
    }


}
