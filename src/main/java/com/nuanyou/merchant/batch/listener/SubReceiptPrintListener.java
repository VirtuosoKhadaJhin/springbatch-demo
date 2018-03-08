package com.nuanyou.merchant.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

/**
 * Created by mylon.sun on 2018/1/29.
 */
public class SubReceiptPrintListener implements ItemWriteListener {

    private Logger logger = LoggerFactory.getLogger(SubReceiptPrintListener.class);


    @Override
    public void beforeWrite(List items) {
        logger.info("**********************beforeWrite**********************");
    }

    @Override
    public void afterWrite(List items) {
        logger.info("**********************afterWrite**********************");
    }

    @Override
    public void onWriteError(Exception exception, List items) {
        logger.info("**********************onWriteError**********************");
    }
}
