package com.samh.personal.common.exception;

import lombok.Data;

/**
 * @description: 全局异常捕获处理工具类
 * @author: WanKai
 * @create: 2019-06-19 10:30
 */
@Data
public class MyException extends RuntimeException {

    private static final long serialVersionUID = -1;
    private int code;
    private String message;
    private String data;

    public MyException(int code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
