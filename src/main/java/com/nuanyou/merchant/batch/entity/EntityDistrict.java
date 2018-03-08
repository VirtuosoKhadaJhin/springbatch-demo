package com.nuanyou.merchant.batch.entity;

import javax.persistence.*;

@Entity
@Table(name = "ny_district")
public class EntityDistrict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "shortname")
    private String shortName;

    @Column(name = "kpname")
    private String kpname;

    @ManyToOne
    @JoinColumn(name = "cityid")
    private EntityCity city;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getKpname() {
        return kpname;
    }

    public void setKpname(String kpname) {
        this.kpname = kpname;
    }

    public EntityCity getCity() {
        return city;
    }

    public void setCity(EntityCity city) {
        this.city = city;
    }
}
