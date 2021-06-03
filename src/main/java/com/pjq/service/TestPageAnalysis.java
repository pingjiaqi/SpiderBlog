package com.pjq.service;

import com.pjq.dao.ContentDao;
import com.pjq.pojo.WebHtml;
import com.pjq.pojo.WebPageNumberHtml;
import com.pjq.pojo.WebPages;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @program: WX
 * @description:
 * @author:
 * @create: 2019-11-28 15:38
 **/
@Service
@Async("taskExecutor")
@Scope("singleton")
public class TestPageAnalysis {

    @Resource
    private ContentDao contentDao;

    @Resource
    private HttpClientResponse httpClientResponse;

    @Resource
    private WebPageHtml webPageHtml;

    private boolean isContinue=true;

    @SneakyThrows
    public WebPages pageAnalysis(String webUrl, String pageUrl, int pages) {
        if (webUrl == null) {
            return null;
        }

        WebPages url = new WebPages();

        //请求页面
        String htmltxt = httpClientResponse.httpClientResponse(webUrl);

        WebHtml webHtml = webPageHtml.showHtml(webUrl);
//        if ((htmltxt == "error" || htmltxt == "time out") && isContinue == true) {
//            url.setPages(pages);
//            url.setNextUrl(webUrl);
//
//            isContinue = false;
//            return url;
//        }
//        isContinue = true;

        //解析html
        Document document = Jsoup.parse(htmltxt);
        Elements elements = document.select(webHtml.getList_html_1()).select(webHtml.getList_html_2());

        for (Element element : elements) {
            String title = element.select(webHtml.getTitle_html_1()).select(webHtml.getTitle_html_2()).text();
            int hashcode = title.hashCode();
            String articleUrl = element.select(webHtml.getArtical_html_1()).select(webHtml.getArtical_html_2()).select("a").attr("href");

            if (!articleUrl.substring(0, 4).equals("http")) {
                articleUrl = pageUrl + articleUrl;
            }

//            String time = element.select(webHtml.getTitle_html()).select("time").attr("datetime");
            String imageUrl = element.select(webHtml.getImg_html_1()).select(webHtml.getImg_html_2()).select("img").attr("src");

            String des = description(articleUrl, webHtml);

            Thread.sleep(10000);

            if (contentDao.selectHashCode(hashcode) == null) {
                contentDao.insertIntoContent(0, title, des, imageUrl, articleUrl, null, hashcode, webHtml.getWebsite_id());
            }

            Thread.sleep(10000);
        }

        //网站下一页的url
        WebPageNumberHtml webPageNumberHtml = webPageHtml.showPagesNumberHtml(webUrl);

        //是否分页
        if (webHtml.getIs_pagination() == 1) {
            int currentPage = 1;
            Elements elements1 = document.select(webPageNumberHtml.getPages_html_1()).select(webPageNumberHtml.getPages_html_2());
            pages++;
            String nextUrl = null;

            for (Element element : elements1) {
                if (String.valueOf(pages).equals(element.text())) {
                    nextUrl = element.select("a").attr("href");
                    break;
                }
            }

            if (elements1.size() < 2) {
                nextUrl = elements1.select("a").attr("href");
            }

            if (nextUrl != null && !nextUrl.substring(0, 4).equals("http")) {
                nextUrl = pageUrl + nextUrl;
            }

            url.setPages(pages);
            url.setNextUrl(nextUrl);
            url.setCurrentPage(currentPage++);
        }
        return url;
    }

    //    帖子内容解析
    public String description(String detailUrl, WebHtml webHtml) {
        String des = null;
        String htmltxt = null;
        try {
            htmltxt = httpClientResponse.httpClientResponse(detailUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document document = Jsoup.parse(htmltxt);
        Elements elements = document.select(webHtml.getDes_html_1()).select(webHtml.getDes_html_2());
        des = elements.first().text();
        return des;
    }

    @SneakyThrows
    public void start(String url) {
        WebPages webPages = new WebPages();
        String pageUrl = url;
        int pages = 1;

        for (int i = 1; i <= pages; i++) {
            System.out.println(url);
            webPages = pageAnalysis(url, pageUrl, pages);
            url = webPages.getNextUrl();

            if (url == null) {
                break;
            }

            pages = webPages.getPages();

            Thread.sleep(10000);
        }
    }
}
