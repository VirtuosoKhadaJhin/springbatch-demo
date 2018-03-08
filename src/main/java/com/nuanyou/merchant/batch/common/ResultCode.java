package com.nuanyou.merchant.batch.common;

/**
 * Created by Byron on 2017/8/29.
 */
public enum ResultCode {

    SUCCESS(0, "成功"),

    FAIL(1, "操作失败"),

    NOT_FOUND(2, "请求资源不存在"),

    MISSING_PARAMETER(3, "缺少必要的参数"),

    JobExecutionAlreadyRunning(4, "任务正在执行中"),

    JobRestart(5, "任务无法被重启"),

    JobInstanceAlreadyComplete(6, "任务已经执行成功"),

    JobParametersInvalid(7, "参数不正确"),

    SubReceiptQrCodeNotFound(8, "未找到该商户的二维码"),

    NoSuchJobExecution(9, "找不到该任务执行序列"),

    JobExecutionNotRunning(10, "任务尚未在执行");

    public final Integer code;

    public final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
