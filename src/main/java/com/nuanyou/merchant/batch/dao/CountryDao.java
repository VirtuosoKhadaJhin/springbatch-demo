package com.nuanyou.merchant.batch.dao;

import com.nuanyou.merchant.batch.entity.EntityCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by mylon.sun on 2018/2/5.
 */
public interface CountryDao extends JpaRepository<EntityCountry, Long>, JpaSpecificationExecutor {
}
