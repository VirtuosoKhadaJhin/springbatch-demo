package com.nuanyou.merchant.batch.processor;

import com.alibaba.fastjson.JSONObject;
import com.nuanyou.merchant.batch.common.ResultCode;
import com.nuanyou.merchant.batch.common.ResultException;
import com.nuanyou.merchant.batch.dao.SubReceiptQrCodeDao;
import com.nuanyou.merchant.batch.entity.EntityMerchant;
import com.nuanyou.merchant.batch.entity.EntitySubReceiptQrCode;
import com.nuanyou.merchant.batch.remote.RemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class SubReceiptPrintProcessor implements ItemProcessor<EntityMerchant, EntityMerchant> {

    private Logger logger = LoggerFactory.getLogger(SubReceiptPrintProcessor.class);

    @Autowired
    private RemoteService remoteService;

    @Autowired
    private SubReceiptQrCodeDao subReceiptQrCodeDao;

    @Override
    public EntityMerchant process(EntityMerchant item) throws Exception {
        String templateJsonStr = "";
        String subReceiptQrCodeUrl = "";
        EntitySubReceiptQrCode qrCode = subReceiptQrCodeDao.findTopByMchIdAndDelFlag(item.getId(), Boolean.FALSE);
        if(qrCode != null){
            subReceiptQrCodeUrl = qrCode.getUrl();
        }
        // 商户指定小票模板
        JSONObject merchantSubReceiptTemplate = remoteService.getMerchantSubReceiptTemplate(item);
        // 商户城市指定小票模板
        if(merchantSubReceiptTemplate == null){
            merchantSubReceiptTemplate = remoteService.getMerchantSubReceiptByCityTemplate(item);
        }
        // 商户国家指定小票模板
        if(merchantSubReceiptTemplate == null){
            merchantSubReceiptTemplate = remoteService.getMerchantSubReceiptByCountryTemplate(item);
        }

        if (merchantSubReceiptTemplate == null) {
            templateJsonStr = remoteService.setSubReceiptQrCodeUrl(merchantSubReceiptTemplate, subReceiptQrCodeUrl);
        }else{
            templateJsonStr = remoteService.setSubReceiptQrCodeUrl(merchantSubReceiptTemplate, subReceiptQrCodeUrl);
        }

        remoteService.putMerchantConfig(templateJsonStr, item.getId() + ".json");

        return item;
    }

}


