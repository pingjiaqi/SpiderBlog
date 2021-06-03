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
 * @description: 柚子AR网站页面数据解析
 * @author:
 * @create:
 **/
@Service
public class YouziarPageAnalysis {
    @Resource
    private ContentDao contentDao;

    @Resource
    private HttpClientResponse httpClientResponse;

    private int pages = 1;

    private boolean isContinue = true;

    private String nextUrl;

    private int currentPage;

    private String pageUrl = "http://www.youziar.com/news/";

    public WebPages page(String webUrl) throws IOException {
        WebPages url = new WebPages();
        String htmltxt = null;
        htmltxt = httpClientResponse.httpClientResponse(webUrl);
        Content content = new Content();
        if (htmltxt == "error") {
            url.setPages(pages);
            url.setNextUrl(nextUrl);
            url.setCurrentPage(currentPage);
            return url;
        }

        //解析html
        Document document = Jsoup.parse(htmltxt);
        Elements elements = document.select("main[class='container']").select("div[class='content-wrap']").select("section[class='article']").select("article[class='post']");
        Elements elements1 = document.select("div[class='pagination pagination-multi']");
        for (Element element : elements) {
            String title = element.select("header").select("h2").select("a").attr("title");
            int hashcode = title.hashCode();
            String articleUrl = element.select("header").select("h2").select("a").attr("href");
            String time = element.select("footer").select("span[class='time']").text();
            String imageUrl = element.select("div[class='post-img']").select("a[class='pic float-left']").select("img").attr("src");

            content.setTitle(title);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = simpleDateFormat.parse(time);
                content.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            content.setUrl(articleUrl);
            content.setImage(imageUrl);
            String des = element.select("article[class='post']").select("p").text();
            if (contentDao.selectHashCode(hashcode) == null) {
                contentDao.insertIntoContent(0, title, des, "http://www.youziar.com" + imageUrl, "http://www.youziar.com" + articleUrl, content.getTime(), hashcode, 2);
            }

        }

        String s = null;
        for (Element element : elements1) {
            s = element.select("li").text();
            s = s.replace("下一页", "");
            s = s.replace(" ", "");
            s = s.substring(s.length() - 1);
            pages = Integer.parseInt(s);
        }
        currentPage = Integer.parseInt(document.select("div[class='pagination pagination-multi']").select("ul").select("li[class='thisclass']").text());
        url.setPages(pages);
        url.setNextUrl(nextUrl);
        url.setCurrentPage(currentPage);
        return url;
    }

    @Async("taskExecutor")
    public void start() throws InterruptedException {
        System.out.println("柚子" + new Date());
        WebPages url1 = new WebPages();
        int i;
        for (i = 1; i <= pages; i++) {
            try {
                url1 = page(pageUrl);
                System.out.println(pageUrl);
                pageUrl = "http://www.youziar.com/news/list_1_" + (i + 1) + ".html";
                pages = url1.getPages();
            } catch (Exception e) {

                e.printStackTrace();
            } finally {
                System.out.println("第"+i+"页");
            }
            Thread.sleep(10000);
        }
        if (i > pages) {
            pageUrl = "http://www.youziar.com/news/";
        }
    }

}
