package com.pjq.dao;

import com.pjq.pojo.WebPageNumberHtml;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WebPageNumberHtmlDao {
    WebPageNumberHtml selectPageHtml(int website_id);
}
