package com.nuanyou.merchant.batch.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ny_country")
public class EntityCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ename")
    private String ename;

    @Column(name = "code")
    private String code;

    @Column(name = "timezone")
    private String timeZone;

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<EntityCity> cities = new ArrayList<EntityCity>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<EntityCity> getCities() {
        return cities;
    }

    public void setCities(List<EntityCity> cities) {
        this.cities = cities;
    }
}
