package com.pjq.dao;

import com.pjq.pojo.Website;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WebsiteDao {

    List<Website> selectAllWebsite();

    Website selectWebsite(String url);
}
