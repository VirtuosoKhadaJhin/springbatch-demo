package com.nuanyou.merchant.batch.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Kevin on 2016/8/13.
 */
@Entity
@Table(name = "ny_merchant")
public class EntityMerchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "kpname")
    private String localName;

    @Column(name = "intro")
    private String intro;

    @Column(name = "telphone")
    private String telphone;

    @Column(name = "address")
    private String address;

    @Column(name = "kpaddress")
    private String localAddress;

    @ManyToOne
    @JoinColumn(name = "districtid")
    private EntityDistrict district;

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

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public EntityDistrict getDistrict() {
        return district;
    }

    public void setDistrict(EntityDistrict district) {
        this.district = district;
    }
}
