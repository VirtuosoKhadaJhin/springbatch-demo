package com.nuanyou.merchant.batch.service;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * Created by mylon.sun on 2018/2/8.
 */
public interface SubReceiptPrintService {

    JSONObject printView(Long merchantId) throws IOException;

}
