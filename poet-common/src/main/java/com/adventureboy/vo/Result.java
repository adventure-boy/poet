package com.adventureboy.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Result<T> implements Serializable {
    private String message = ""; //处理信息
    private Integer code = 0; //代码
    private Boolean isSuccess = true;//成功标志
    private T result;//返回实体
    private Long timestamp = System.currentTimeMillis(); //时间戳

    public Result<T> error500(String message) {
        this.message = message;
        this.code = 500;
        this.isSuccess = false;
        return this;
    }

    public Result<T> success200(T result) {
        this.message = "成功";
        this.code = 200;
        this.isSuccess = true;
        this.result = result;
        return this;
    }
}
