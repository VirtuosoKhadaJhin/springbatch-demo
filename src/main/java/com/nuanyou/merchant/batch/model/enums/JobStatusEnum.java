package com.nuanyou.merchant.batch.model.enums;

/**
 * Created by mylon.sun on 2018/1/31.
 */
public enum JobStatusEnum {
    STARTED(1, "进行中", "STARTED"),
    FAILED(2, "已失败", "FAILED"),
    COMPLETED(3, "已完成", "COMPLETED"),
    UNKNOWN(4, "未知状态", "UNKNOWN"),
    STARTING(5, "正在启动", "STARTING"),
    STOPPING(5, "已经停止", "STOPPING");

    private Integer key;

    private String name;

    private String value;

    JobStatusEnum() {
    }

    JobStatusEnum(Integer key, String name, String value) {
        this.key = key;
        this.name = name;
        this.value = value;
    }

    public static JobStatusEnum toEnum(int key) {
        JobStatusEnum[] values = JobStatusEnum.values();
        for (JobStatusEnum status : values)
            if (key == status.getKey()) {
                return status;
            }
        throw new IllegalArgumentException("Cannot create evalue from value: " + key + "!");
    }

    public static JobStatusEnum toEnums(String value) {
        for (JobStatusEnum type : JobStatusEnum.values()) {
            if (value.equals(type.getValue())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Cannot create evalue from value: " + value + "!");
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
