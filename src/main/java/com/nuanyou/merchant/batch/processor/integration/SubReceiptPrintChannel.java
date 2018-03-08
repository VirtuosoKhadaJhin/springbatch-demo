package com.nuanyou.merchant.batch.processor.integration;

import com.nuanyou.merchant.batch.entity.EntityMerchant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import javax.annotation.Resource;

/**
 * Created by mylon.sun on 2018/2/1.
 */
public class SubReceiptPrintChannel implements ItemProcessor<EntityMerchant, EntityMerchant> {

    private Logger LOGGER = LoggerFactory.getLogger(SubReceiptPrintChannel.class);

    @Resource(name = "subReceiptPrintJobInputChannel")
    private MessageChannel subReceiptPrintJobInputChannel;

    @Override
    public EntityMerchant process(EntityMerchant item) throws Exception {
        boolean sendResult = subReceiptPrintJobInputChannel.send(new GenericMessage(item));
        LOGGER.info("======================= subReceiptPrintJobInputChannel >> sendResult：" + sendResult + "=======================");
        return item;
    }

}
