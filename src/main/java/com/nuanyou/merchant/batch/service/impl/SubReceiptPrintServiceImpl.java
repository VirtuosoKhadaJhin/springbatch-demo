package com.nuanyou.merchant.batch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.nuanyou.merchant.batch.service.SubReceiptPrintService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by mylon.sun on 2018/2/8.
 */
@Service
public class SubReceiptPrintServiceImpl implements SubReceiptPrintService {

    @Value("${s3.region}")
    private String region;

    @Value("${s3.bucket}")
    private String bucket;

    @Value("${s3.userSubReceiptPrint}")
    private String userSubReceiptPrint;

    @Override
    public JSONObject printView(Long merchantId) throws IOException {
        AmazonS3 client = AmazonS3ClientBuilder.standard().withRegion(region).build();
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, userSubReceiptPrint + merchantId + ".json");
        S3Object object = client.getObject(getObjectRequest);
        InputStream objectData = object.getObjectContent();
        StringBuilder json = new StringBuilder();
        BufferedReader reader = new BufferedReader(new
                InputStreamReader(objectData));
        String inputLine = null;
        while ((inputLine = reader.readLine()) != null) {
            json.append(inputLine);
        }
        JSONObject response = JSONObject.parseObject(json.toString());
        reader.close();
        return response;
    }

}
