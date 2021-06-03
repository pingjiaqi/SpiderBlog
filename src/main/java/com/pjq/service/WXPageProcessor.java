//package com.pjq.service;
//
//import com.pjq.dao.ContentDao;
//import com.pjq.dao.WebsiteDao;
//import com.pjq.pojo.Content;
//import com.pjq.pojo.Website;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Service;
//import us.codecraft.webmagic.Page;
//import us.codecraft.webmagic.Site;
//import us.codecraft.webmagic.processor.PageProcessor;
//import us.codecraft.webmagic.selector.Selectable;
//
//import javax.annotation.Resource;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//
//@Scope("prototype")
//@Service
//public class WXPageProcessor implements PageProcessor {
//    private Site site = Site.me().setSleepTime(1000);
//
//    @Resource
//    private ContentDao contentDao;
//
//    @Resource
//    private WebsiteDao websiteDao;
//
//
//    @Override
//    public void process(Page page) {
//        ArrayList<Content> contentArrayList = new ArrayList<>();
//        ArrayList<String> postUrlList = new ArrayList<>();
//        ArrayList<Selectable> selectableArrayList = new ArrayList<>();
//        selectableArrayList = (ArrayList<Selectable>) page.getHtml().xpath("//div[@class='blog-listing-el']/div").nodes();
//
//        for (Selectable s : selectableArrayList) {
//            Content content = new Content();
//            String title = s.xpath("//article/div[@class='post-body']/h2").css("a", "title").toString();
//            int hashcode = title.hashCode();
////            Integer hashcode1=contentDao.selectHashCode(hashcode);
////            if(hashcode1!=null)
////            {
////                return;
////            }
//            String posturl = s.xpath("//article/div[@class='post-body']/h2/a").links().toString();
//            postUrlList.add(posturl);
//
//            String url = posturl.substring(0, 10);
//            Website website = websiteDao.selectWebsite(url);
//
//            String time = s.xpath("//article/div[@class='post-body']/div/div/span").css("time", "datetime").toString();
////            String contentID=s.xpath("");
//            String t = "T";
//            String t1 = "+00:00";
//            time = time.replace(t, " ");
//            time = time.replace(t1, "");
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            try {
//                Date date = simpleDateFormat.parse(time);
//                content.setTime(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            String image = s.xpath("//article/div[@class='post-header']/div/div/a/span").css("img", "src").toString();
//            content.setContentId(0);
//            content.setTitle(title);
//            content.setTitleHashCode(hashcode);
//            content.setUrl(posturl);
//            content.setImage(image);
//            contentArrayList.add(content);
//            content.setWebsiteId(website.getId());
//        }
//
//        for (Content content : contentArrayList) {
//            Integer hashcode = contentDao.selectHashCode(content.getTitle_hashcode());
//            if (hashcode == null) {
//                contentDao.insertIntoContent(content.getContentId(), content.getTitle(), content.getDescription(),
//                        content.getImage(), content.getUrl(), content.getTime(), content.getTitleHashCode(), content.getWebsiteId());
//                // TODO:推送接口
//
//            }
//        }
//        int pages = Integer.parseInt(page.getHtml().xpath("//div[@class='pagination-number']/span[@class='page-numbers current']/text()").toString());
//        String pagesurl = "/category/augmented-reality/page/" + String.valueOf(++pages);
//        page.addTargetRequest(pagesurl);
//    }
//
//    @Override
//    public Site getSite() {
//        return site;
//    }
//
//}
