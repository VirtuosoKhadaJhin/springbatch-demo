package com.nuanyou.merchant.batch.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Byron on 2017/8/29.
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("data")
    private T data;

    public Result() {
        this.code = ResultCode.SUCCESS.code;
        this.msg = ResultCode.SUCCESS.message;
    }

    public Result(ResultException e) {
        this.code = e.getCode();
        this.msg = e.getMessage();
    }

    public Result(ResultCode code) {
        this.code = code.code;
        this.msg = code.getMessage();
    }

    public Result(ResultCode code, String message) {
        this.code = code.code;
        this.msg = code.message + message;
    }

    public Result(T t) {
        this(ResultCode.SUCCESS, t);
    }

    public Result(ResultCode code, T data) {
        this(code);
        this.data = data;
    }

    public boolean isSuccess() {
        return ResultCode.SUCCESS.code.equals(code);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
