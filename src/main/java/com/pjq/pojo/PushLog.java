package com.pjq.pojo;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class PushLog implements Comparable<PushLog>{
    //id
    @Column(name = "id")
    private int id;
    //帖子id
    @Column(name = "content_id")
    private int content_id;
    //推送的次数
    @Column(name = "push_times")
    private int push_times;
    //推送的时间
    @Column(name = "lastes_time")
    private Date lastes_time;



    @Override
    public int compareTo(PushLog o) {
        return this.push_times-o.getPush_times();
    }
}
