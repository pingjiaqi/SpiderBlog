package com.pjq.dao;

import com.pjq.pojo.WebHtml;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WebHtmlDao {
        WebHtml selectAllHtml(int websiteId);
}
