package com.nuanyou.merchant.batch.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ny_city")
public class EntityCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "countryid")
    private EntityCountry country;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<EntityDistrict> districts = new ArrayList<EntityDistrict>();

    public EntityCity() {
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EntityCountry getCountry() {
        return country;
    }

    public void setCountry(EntityCountry country) {
        this.country = country;
    }

    public List<EntityDistrict> getDistricts() {
        return districts;
    }

    public void setDistricts(List<EntityDistrict> districts) {
        this.districts = districts;
    }
}
