package com.samh.personal.common.exception;

import com.google.gson.JsonObject;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @program: personal
 * @description: 公共消息返回实体类
 * @author: WanKai
 * @create: 2020-10-20 09:35
 */
@Slf4j
@Data
@ApiModel(value = "公共消息返回实体")
public class ResultVO implements Serializable {
    private static final long serialVersionUID = -1;

    private Integer status;
    private String msg;
    private Object data;

    public ResultVO(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResultVO(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        this.data = null;
    }

    public static ResultVO success() {
        return new ResultVO(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg(), null);
    }

    public static ResultVO success(Object data) {
        return new ResultVO(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg(), data);
    }

    public static ResultVO success(int status, String msg, Object data) {
        return new ResultVO(status, msg, data);
    }

    public static ResultVO success(int status, String msg) {
        return new ResultVO(status, msg);
    }

    public static ResultVO error(int status, String msg, String data) {
        log.error("接口调用出错，错误码：" + status + " 错误信息：" + msg + " 具体报错信息：" + data);
        return new ResultVO(status, msg, data);
    }

    public static ResultVO error(int status, String msg) {
        log.error("接口调用出错，错误码：" + status + " 错误信息：" + msg);
        return new ResultVO(status, msg, null);
    }

    public static void returnJson(HttpServletResponse response, int status, String msg) {
        try {
            PrintWriter writer = response.getWriter();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("status", status);
            jsonObject.addProperty("msg", msg);
            writer.print(jsonObject);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
