package com.pjq.service;

import com.pjq.dao.ContentDao;
import com.pjq.dao.PushLogDao;
import com.pjq.pojo.Content;
import com.pjq.pojo.PushLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @program:
 * @description: 微信推送
 * @author:
 * @create: 2019-10-30 17:28
 **/
@Service
public class WeChatPush {

    @Resource
    private ContentDao contentDao;

    @Resource
    private PushLogDao pushLogDao;

    public ArrayList<Content> sendPost() {
        ArrayList<Content> msg = new ArrayList<>();
        ArrayList<Content> contents = new ArrayList<>();
        contents = contentDao.selectAllContent();
        List<PushLog> pushLogs = new ArrayList<>();
        for (Content content : contents) {
            PushLog pushLog = new PushLog();
            pushLog.setContent_id(content.getTitle().hashCode());
            pushLog.setLastes_time(new Date());
            try {
                int id = (content.getTitle().hashCode());
                int times = pushLogDao.selectTimes(id);
                pushLog.setPush_times(times + 1);
                pushLogDao.delect(id);
            } catch (Exception e) {
                pushLog.setPush_times(1);
                msg.add(content);
            }
            pushLogs.add(pushLog);
        }
        pushLogDao.insertLog(pushLogs);
        if (msg.size() < 10) {
            List<PushLog> pushLogList = new ArrayList<>();
            pushLogList = pushLogDao.selelAllLog();
            Collections.sort(pushLogList);
            for (int i = 0; i <= 10; i++) {
//                Content content=contentDao.selectHashCode(pushLogList.get(i).getContent_id());
                msg.add(contentDao.selectHashCode(pushLogList.get(i).getContent_id()));
            }
        }
        return msg;
    }
}
