package com.nuanyou.merchant.batch.processor;

import com.nuanyou.merchant.batch.dao.SubReceiptQrCodeDao;
import com.nuanyou.merchant.batch.entity.EntityMerchant;
import com.nuanyou.merchant.batch.entity.EntitySubReceiptQrCode;
import com.nuanyou.merchant.batch.entity.EntitySubReceiptQrCodeConfig;
import com.nuanyou.merchant.batch.model.CountryVo;
import com.nuanyou.merchant.batch.remote.RemoteService;
import com.nuanyou.merchant.batch.service.SubReceiptQrCodeConfigService;
import com.nuanyou.merchant.batch.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class SubReceiptCodeProcessor implements ItemProcessor<EntityMerchant, EntityMerchant> {

    private Logger LOGGER = LoggerFactory.getLogger(SubReceiptCodeProcessor.class);

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

        CountryVo countryVo = remoteService.queryCountryBaseInfo(item.getDistrict().getCity().getCountry().getId().toString());
        String pushLinkUrl = merchantQrCodeConfig.getPushLinkUrl();
        if (merchantQrCodeConfig.getPushLinkUrl() == null) {
            String url = countryVo.getUrl();
            pushLinkUrl = url + "/view/order/morediscount.html?mchid="
                    + item.getId() + "&source=qplcid_" + item.getId() + "&from=subreceipt_code";
        }
        String pushContent = remoteService.makePushContent(merchantQrCodeConfig, pushLinkUrl);

        if(qrCode == null){
            qrCode = new EntitySubReceiptQrCode();
            qrCode.setMchId(item.getId());
            qrCode.setCreateTime(new Date());
            qrCode.setDelFlag(Boolean.FALSE);
            qrCode.setExpireTime(DateUtils.extendExpireTime(new Date()));
        }
        qrCode.setUpdateTime(new Date());
        qrCode.setTimestamp(merchantQrCodeConfig.getTimestamp());
        qrCode.setSceneId("5" + item.getId());
        qrCode.setPushContent(pushContent);
        remoteService.generateQrCode(qrCode);
        subReceiptQrCodeDao.save(qrCode);
        remoteService.updatePushContent(qrCode.getSceneId(), pushContent, pushContent);
        return item;
    }

}


