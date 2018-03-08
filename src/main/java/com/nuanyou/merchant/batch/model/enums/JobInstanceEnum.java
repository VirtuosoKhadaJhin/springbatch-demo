package com.nuanyou.merchant.batch.model.enums;

/**
 * Created by mylon.sun on 2018/1/31.
 */
public enum JobInstanceEnum {

    subReceiptPrintsJob(11, "批量打印模板生成", "subReceiptPrintsJob"),
    subReceiptPrintJob(1, "商户打印模板生成", "subReceiptPrintJob"),
    subReceiptCodesJob(12, "批量关注码生成", "subReceiptCodesJob"),
    subReceiptCodeJob(2, "商户关注码生成", "subReceiptCodeJob"),
    subReceiptCodeExpiresJob(13, "批量关注码过期更新", "subReceiptCodeExpiresJob"),
    subReceiptCodeExpireJob(3, "商户关注码过期更新", "subReceiptCodeExpireJob"),
    subReceiptCodeDocsJob(14, "批量关注码内容更新", "subReceiptCodeDocsJob"),
    subReceiptCodeDocJob(4, "商户关注码内容更新", "subReceiptCodeDocJob");

    private Integer key;

    private String name;

    private String value;

    JobInstanceEnum() {
    }

    JobInstanceEnum(Integer key, String name, String value) {
        this.key = key;
        this.name = name;
        this.value = value;
    }

    public static JobInstanceEnum toEnum(int key) {
        JobInstanceEnum[] values = JobInstanceEnum.values();
        for (JobInstanceEnum status : values)
            if (key == status.getKey()) {
                return status;
            }
        throw new IllegalArgumentException("Cannot create evalue from value: " + key + "!");
    }

    public static JobInstanceEnum toEnums(String value) {
        for (JobInstanceEnum type : JobInstanceEnum.values()) {
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
