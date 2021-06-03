package com.pjq.pojo;

import lombok.Data;

@Data
public class Result {
    private String code;
    private String message;
    private Object result;

    public Result(){

    }

    public Result(String code, String message, Object result){
        this.code=code;
        this.message=message;
        this.result=result;
    }
}
