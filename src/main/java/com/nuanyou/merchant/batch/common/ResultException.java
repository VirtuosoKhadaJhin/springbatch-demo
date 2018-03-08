package com.nuanyou.merchant.batch.common;

/**
 * Created by Byron on 2017/8/29.
 */
public class ResultException extends RuntimeException {

    private int code;

    public ResultException(ResultCode code) {
        super(code.message);
        this.code = code.code;
    }

    public ResultException(ResultCode code, Object message) {
        super(code.getCode() + "：" + String.valueOf(message));
        this.code = code.code;
    }

    public ResultException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
