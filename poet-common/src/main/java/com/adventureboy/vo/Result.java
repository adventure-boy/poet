package com.adventureboy.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Result<T> implements Serializable {
    private String message = ""; //处理信息
    private Integer code = 0; //代码
    private Boolean isSuccess;//成功标志
    private T result;//返回实体
    private Long timestamp = System.currentTimeMillis(); //时间戳
}
