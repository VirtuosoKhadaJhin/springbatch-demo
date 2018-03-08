package com.nuanyou.merchant.batch.processor;

import com.nuanyou.merchant.batch.common.ResultCode;
import com.nuanyou.merchant.batch.common.ResultException;
import com.nuanyou.merchant.batch.dao.SubReceiptQrCodeDao;
import com.nuanyou.merchant.batch.entity.EntityMerchant;
import com.nuanyou.merchant.batch.entity.EntitySubReceiptQrCode;
import com.nuanyou.merchant.batch.entity.EntitySubReceiptQrCodeConfig;
import com.nuanyou.merchant.batch.remote.RemoteService;
import com.nuanyou.merchant.batch.service.SubReceiptQrCodeConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class SubReceiptCodeDocProcessor implements ItemProcessor<EntityMerchant, EntityMerchant> {

    private Logger LOGGER = LoggerFactory.getLogger(SubReceiptCodeDocProcessor.class);

    @Autowired
    private RemoteService remoteService;

    @Autowired
    private SubReceiptQrCodeDao subReceiptQrCodeDao;

    @Autowired
    private SubReceiptQrCodeConfigService subReceiptQrCodeConfigService;

    @Override
    public EntityMerchant process(EntityMerchant item) throws Exception {
        EntitySubReceiptQrCode qrCode = subReceiptQrCodeDao.findTopByMchIdAndDelFlag(item.getId(), Boolean.FALSE);
        EntitySubReceiptQrCodeConfig merchantQrCodeConfig = subReceiptQrCodeConfigService.getMerchantQrCodeConfig(item);
        if(qrCode == null){
            return item;
        }
        if (!merchantQrCodeConfig.getTimestamp().equals(qrCode.getTimestamp())) {
            String pushContent = remoteService.makePushContent(merchantQrCodeConfig, merchantQrCodeConfig.getPushLinkUrl());
            qrCode.setTimestamp(merchantQrCodeConfig.getTimestamp());
            qrCode.setPushContent(pushContent);
            qrCode.setUpdateTime(new Date());
            subReceiptQrCodeDao.save(qrCode);
            remoteService.updatePushContent(qrCode.getSceneId(), pushContent, pushContent);
        }
        return item;
    }


}


