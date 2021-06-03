package com.pjq.pojo;

import lombok.Data;

@Data
public class WebPages {
    //总页号
    int pages;
    //下一页的url
    String nextUrl;
    //当前页号
    int currentPage;
}
