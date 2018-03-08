package com.nuanyou.merchant.batch.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mylon.sun on 2018/2/1.
 */
@Entity
@Table(name = "ny_subreceipt_qrcode_cfg")
public class EntitySubReceiptQrCodeConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "countryid")
    private Long countryId;

    @Column(name = "cityid")
    private Long cityId;

    @Column(name = "districtid")
    private Long districtId;

    @Column(name = "mchid")
    private Long mchId;

    @Column(name = "pushtype")
    private String pushType;

    @Column(name = "pushtitle")
    private String pushTitle;

    @Column(name = "pushdesc")
    private String pushDesc;

    @Column(name = "pushimgurl")
    private String pushImgUrl;

    @Column(name = "pushlinkurl")
    private String pushLinkUrl;

    @Column(name = "createtime")
    private Date createTime;

    @Column(name = "updatetime")
    private Date updateTime;

    @Column(name = "delflag")
    private Boolean delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getMchId() {
        return mchId;
    }

    public void setMchId(Long mchId) {
        this.mchId = mchId;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getPushTitle() {
        return pushTitle;
    }

    public void setPushTitle(String pushTitle) {
        this.pushTitle = pushTitle;
    }

    public String getPushDesc() {
        return pushDesc;
    }

    public void setPushDesc(String pushDesc) {
        this.pushDesc = pushDesc;
    }

    public String getPushImgUrl() {
        return pushImgUrl;
    }

    public void setPushImgUrl(String pushImgUrl) {
        this.pushImgUrl = pushImgUrl;
    }

    public String getPushLinkUrl() {
        return pushLinkUrl;
    }

    public void setPushLinkUrl(String pushLinkUrl) {
        this.pushLinkUrl = pushLinkUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }
}
