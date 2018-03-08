package com.nuanyou.merchant.batch.dao;

import com.nuanyou.merchant.batch.entity.EntitySubReceiptQrCodeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by mylon.sun on 2018/2/1.
 */
public interface SubReceiptQrCodeConfigDao extends JpaRepository<EntitySubReceiptQrCodeConfig, Long>, JpaSpecificationExecutor {

    EntitySubReceiptQrCodeConfig findByMchIdAndDelFlag(Long mchId, Boolean delFlag);

    EntitySubReceiptQrCodeConfig findByCityIdAndDelFlag(Long cityId, Boolean delFlag);

    EntitySubReceiptQrCodeConfig findByCountryIdAndDelFlag(Long countryId, Boolean delFlag);

    EntitySubReceiptQrCodeConfig findByDistrictIdAndDelFlag(Long districtId, Boolean delFlag);

}
