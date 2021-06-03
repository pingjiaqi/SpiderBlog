package com.pjq.pojo;

import lombok.Data;

import javax.persistence.Column;

/**
 * @program: WX
 * @description:
 * @author:
 * @create: 2019-11-28 15:39
 **/

@Data
public class WebHtml {
    @Column(name = "id")
    private int id;

    @Column(name = "website_id")
    private int website_id;

    @Column(name = "module")
    private String module;

    @Column(name = "list_html_1")
    private String list_html_1;

    @Column(name = "list_html_2")
    private String list_html_2;

    @Column(name = "title_html_1")
    private String title_html_1;

    @Column(name = "title_html_2")
    private String title_html_2;

    @Column(name = "artical_html_1")
    private String artical_html_1;

    @Column(name = "artical_html_2")
    private String artical_html_2;

    @Column(name = "img_html_1")
    private String img_html_1;

    @Column(name = "img_html_2")
    private String img_html_2;

    @Column(name = "time_html")
    private String time_html;

    private String des_html_1;

    private String des_html_2;

    @Column(name = "is_pagination")
    private int is_pagination;

}
