package com.nuanyou.merchant.batch.service.impl;

import com.nuanyou.merchant.batch.dao.SubReceiptQrCodeConfigDao;
import com.nuanyou.merchant.batch.entity.EntityMerchant;
import com.nuanyou.merchant.batch.entity.EntitySubReceiptQrCodeConfig;
import com.nuanyou.merchant.batch.service.SubReceiptQrCodeConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mylon.sun on 2018/2/1.
 */
@Service
public class SubReceiptQrCodeConfigServiceImpl implements SubReceiptQrCodeConfigService {

    @Autowired
    private SubReceiptQrCodeConfigDao subReceiptQrCodeConfigDao;

    @Override
    public EntitySubReceiptQrCodeConfig getMerchantQrCodeConfig(EntityMerchant item) {
        EntitySubReceiptQrCodeConfig mchCfg = subReceiptQrCodeConfigDao.findByMchIdAndDelFlag(item.getId(), Boolean.FALSE);
        if(mchCfg != null){
            return mchCfg;
        }
        EntitySubReceiptQrCodeConfig districtCfg = subReceiptQrCodeConfigDao.findByDistrictIdAndDelFlag(item.getDistrict().getId(), Boolean.FALSE);
        if(districtCfg != null){
            return districtCfg;
        }
        EntitySubReceiptQrCodeConfig cityCfg = subReceiptQrCodeConfigDao.findByCityIdAndDelFlag(item.getDistrict().getCity().getId(), Boolean.FALSE);
        if(cityCfg != null){
            return cityCfg;
        }
        EntitySubReceiptQrCodeConfig countryCfg = subReceiptQrCodeConfigDao.findByCountryIdAndDelFlag(item.getDistrict().getCity().getCountry().getId(), Boolean.FALSE);
        if(countryCfg != null){
            return countryCfg;
        }
        return null;
    }

}
