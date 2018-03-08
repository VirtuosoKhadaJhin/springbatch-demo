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
public class SubReceiptCodeDocChannel implements ItemProcessor<EntityMerchant, EntityMerchant> {

    private Logger LOGGER = LoggerFactory.getLogger(SubReceiptCodeDocChannel.class);

    @Resource(name = "subReceiptCodeDocJobInputChannel")
    private MessageChannel subReceiptCodeDocJobInputChannel;

    @Override
    public EntityMerchant process(EntityMerchant item) throws Exception {
        boolean sendResult = subReceiptCodeDocJobInputChannel.send(new GenericMessage(item));
        LOGGER.info("======================= subReceiptCodeExpireJobInputChannel >> sendResult：" + sendResult + "=======================");
        return item;
    }

}
