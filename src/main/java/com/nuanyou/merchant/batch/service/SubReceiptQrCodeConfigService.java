package com.nuanyou.merchant.batch.service;

import com.nuanyou.merchant.batch.entity.EntityMerchant;
import com.nuanyou.merchant.batch.entity.EntitySubReceiptQrCodeConfig;

/**
 * Created by mylon.sun on 2018/2/1.
 */
public interface SubReceiptQrCodeConfigService {

    EntitySubReceiptQrCodeConfig getMerchantQrCodeConfig(EntityMerchant mchId);

}
