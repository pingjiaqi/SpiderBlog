package com.pjq.pojo;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class Content {
    //内容id
    @Column(name = "id")
    private int id;
    //ar资讯文章id
    @Column(name = "content_id")
    private int contentId;
    //ar资讯标题
    @Column(name = "title")
    private String title;
    //标题的hashcode
    @Column(name = "title_hashcode")
    private int titleHashCode;
    //文章简要描述
    @Column(name = "description")
    private String description;
    //文章封面url
    @Column(name = "image")
    private String image;
    //文章的url
    @Column(name = "url")
    private String url;
    //文章发布时间
    @Column(name = "time")
    private Date time;
    //所属网站的id
    @Column(name = "website_id")
    private int websiteId;
}
