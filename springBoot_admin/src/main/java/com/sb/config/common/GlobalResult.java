package com.sb.config.common;

import lombok.Data;

import java.io.Serializable;

/**
 * author: dyq
 * Time: 2023/3/17
 * description: 描述
 */
@Data
public class GlobalResult implements Serializable {
    private Integer code;
    private String msg;
    private Object Data;

    public static GlobalResult success(Object data) {
        return success(200,"操作成功",data);
    }

    public static GlobalResult success(int code, String msg, Object data) {
        GlobalResult result =  new GlobalResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static GlobalResult fail(String msg) {
        return fail(400,msg,null);
    }

    public static GlobalResult fail(int code, String msg, Object data) {
        GlobalResult result =  new GlobalResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
