package com.nuanyou.merchant.batch.processor;

import com.nuanyou.merchant.batch.common.ResultCode;
import com.nuanyou.merchant.batch.common.ResultException;
import com.nuanyou.merchant.batch.dao.SubReceiptQrCodeDao;
import com.nuanyou.merchant.batch.entity.EntityMerchant;
import com.nuanyou.merchant.batch.entity.EntitySubReceiptQrCode;
import com.nuanyou.merchant.batch.remote.RemoteService;
import com.nuanyou.merchant.batch.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class SubReceiptCodeExpireProcessor implements ItemProcessor<EntityMerchant, EntityMerchant> {

    private Logger LOGGER = LoggerFactory.getLogger(SubReceiptCodeExpireProcessor.class);

    @Autowired
    private RemoteService remoteService;

    @Autowired
    private SubReceiptQrCodeDao subReceiptQrCodeDao;

    @Override
    public EntityMerchant process(EntityMerchant item) throws Exception {
        EntitySubReceiptQrCode qrCode = subReceiptQrCodeDao.findTopByMchIdAndDelFlag(item.getId(), Boolean.FALSE);
        if(qrCode == null){
            return item;
        }

        if (qrCode.getExpireTime().compareTo(DateUtils.getDateBeforeNowDay(new Date())) > 0) {
            qrCode.setExpireTime(DateUtils.extendExpireTime(qrCode.getExpireTime()));
            qrCode.setUpdateTime(new Date());
            remoteService.generateQrCode(qrCode);
            subReceiptQrCodeDao.save(qrCode);
        }
        return item;
    }

}


