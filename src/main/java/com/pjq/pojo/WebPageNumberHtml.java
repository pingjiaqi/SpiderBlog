package com.pjq.pojo;

import lombok.Data;

/**
 * @program: WX
 * @description:
 * @author:
 * @create: 2019-12-02 11:00
 **/
@Data
public class WebPageNumberHtml {
    private int id;
    private int website_id;
    private String pages_html_1;
    private String pages_html_2;
    private String current_page_html;
}
