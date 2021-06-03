package com.pjq.dao;

import com.pjq.pojo.Content;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Date;

@Mapper
public interface ContentDao {

    void insertIntoContent(@Param("contentId") int contentId, @Param("title") String title, @Param("description") String description,
                           @Param("image") String image, @Param("url") String url, @Param("time") Date time, @Param("titleHashCode") int titleHashCode, @Param("websiteId") int websiteId);

    void insertIntoID(int id);

    String selectContent(String title);

    Content selectHashCode(int titleHashCode);

    void insertWebSiteId(int website_id);

    void insertDescription(String description);

    ArrayList<Content> selectAllContent();

    String selectsome(String title);



}
