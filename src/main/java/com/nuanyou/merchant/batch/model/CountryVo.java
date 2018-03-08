package com.nuanyou.merchant.batch.model;

/**
 * Created by mylon.sun on 2018/1/24.
 */
public class CountryVo {

    private Integer Id;

    private String name;

    private String ename;

    private String sort;

    private String code;

    private String langCode;

    private String nydomain;

    private String paymentdomain;

    private String userdomain;

    private Integer display;

    private String timezone;

    private String createtime;

    private String radio;

    private String rateid;

    private String url;

    private String currencySymbol;

    private String currencySymbolPosition;

    @Override
    public String toString() {
        return "CountryVo{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", ename='" + ename + '\'' +
                ", sort='" + sort + '\'' +
                ", code='" + code + '\'' +
                ", langCode='" + langCode + '\'' +
                ", nydomain='" + nydomain + '\'' +
                ", paymentdomain='" + paymentdomain + '\'' +
                ", userdomain='" + userdomain + '\'' +
                ", display=" + display +
                ", timezone='" + timezone + '\'' +
                ", createtime='" + createtime + '\'' +
                ", radio='" + radio + '\'' +
                ", rateid='" + rateid + '\'' +
                ", url='" + url + '\'' +
                ", currencySymbol='" + currencySymbol + '\'' +
                ", currencySymbolPosition='" + currencySymbolPosition + '\'' +
                '}';
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getNydomain() {
        return nydomain;
    }

    public void setNydomain(String nydomain) {
        this.nydomain = nydomain;
    }

    public String getPaymentdomain() {
        return paymentdomain;
    }

    public void setPaymentdomain(String paymentdomain) {
        this.paymentdomain = paymentdomain;
    }

    public String getUserdomain() {
        return userdomain;
    }

    public void setUserdomain(String userdomain) {
        this.userdomain = userdomain;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getRateid() {
        return rateid;
    }

    public void setRateid(String rateid) {
        this.rateid = rateid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencySymbolPosition() {
        return currencySymbolPosition;
    }

    public void setCurrencySymbolPosition(String currencySymbolPosition) {
        this.currencySymbolPosition = currencySymbolPosition;
    }
}
