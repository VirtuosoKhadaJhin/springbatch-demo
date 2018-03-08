package com.nuanyou.merchant.batch.dao;

import com.nuanyou.merchant.batch.entity.EntitySubReceiptQrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by mylon.sun on 2018/1/30.
 */
public interface SubReceiptQrCodeDao extends JpaRepository<EntitySubReceiptQrCode, Long>, JpaSpecificationExecutor {

    EntitySubReceiptQrCode findTopByMchIdAndDelFlag(Long mchId, Boolean delFlag);

}
