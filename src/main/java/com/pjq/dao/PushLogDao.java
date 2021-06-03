package com.pjq.dao;

import com.pjq.pojo.PushLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pjq
 */
@Mapper
public interface PushLogDao {
    void insertLog(@Param("contentList") List<PushLog> chooseList);

    int selectTimes(@Param("content_id")int content_id);

    void delect(@Param("content_id")int contentId);

    List<PushLog> selelAllLog();
}
