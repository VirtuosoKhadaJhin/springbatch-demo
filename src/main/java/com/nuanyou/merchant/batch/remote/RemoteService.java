package com.nuanyou.merchant.batch.remote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.common.collect.Maps;
import com.nuanyou.merchant.batch.common.Result;
import com.nuanyou.merchant.batch.common.ResultException;
import com.nuanyou.merchant.batch.entity.EntityCity;
import com.nuanyou.merchant.batch.entity.EntityMerchant;
import com.nuanyou.merchant.batch.entity.EntitySubReceiptQrCode;
import com.nuanyou.merchant.batch.entity.EntitySubReceiptQrCodeConfig;
import com.nuanyou.merchant.batch.model.CountryVo;
import com.nuanyou.merchant.batch.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mylon.sun on 2018/2/1.
 */
@Component
public class RemoteService {

    public static final String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKrN7VkgMo02hYK7VuJ7KgBGQB8VBxW2KJI7eUk+95BhmcEDAZT5SKxrihjEHDerX+31TkrIddgl3KaHmaHHwAzVQV6fG3G4XXxG3EiqkKQySp4SVN5b9++nzit+ddofi4mXEt8MlkYzWV9nNp7guUQCXy9frp4KyfHMtsBS930ZAgMBAAECgYEAmMZGhNCKxso8kxlz9nHJuKMdWW/afW4ITfwKWRyMHMVf3EcPFCwA98/cnphS0Oxlipc+px80YNhEy2NAZHchbCFN25d0pSmC0WY1a70W7MYbkblAeLoLBNPNEw9OmYmog6lz+FUa0Oz1Oq9DwZRva5qqonLUKC1c+3yEZw/ioAECQQDmF+QB50ZE26/kn67LtOfKAuYMQ0lHFL0A8pszwJWbPNoKpaGgpnLA4zJiI78aUn1eGoG57Qp9h3EbdbUbFmkZAkEAvgkeWx5XAAVyko06bqY9EZ7gH7z2wF25YnRzXcW3E2BIuknDCQqQ7DstP8aHFdunnMlHVMA/rGJOIZ4r58g0AQJAE+OiyOtV7qPSy39mG6OymYqwmgTC88r+H3PZKJsQE5AqBNuWYg2hQ70f4M3YOg1BWv4NkqXDz2ACze3ZztKcGQJBAIk9CpghTBEu3fQqW/WGxnmQNCmXjNeVmAkbMimZXMJ4eW1XUauY3tpLTj1NgUbuz5gx3/q7sAAtKmGq2ehUtAECQQDC8npeJa1+vKaAcrYdcIEjEKnU9OubmwYGWpfEv1ljHwz6djVYaLAgN6TvkUdHGQsmmGV0+CanSBAZBkh2PDVR";

    @Value("${nuanyou.domain}")
    private String userUrl;

    @Value("${common.domain}")
    private String commonUrl;

    @Value("${s3.region}")
    private String region;

    @Value("${s3.bucket}")
    private String bucket;

    @Value("${s3.userSubReceiptPrint}")
    private String userSubReceiptPrint;

    @Value("${s3.userSubReceiptTemplate}")
    private String userSubReceiptTemplate;

    /**
     * 生成二维码
     *
     * @param subReceiptQrCode
     * @throws Exception
     */
    public void generateQrCode(EntitySubReceiptQrCode subReceiptQrCode) throws Exception {
        Long expires = Math.min((subReceiptQrCode.getExpireTime().getTime() - subReceiptQrCode.getCreateTime().getTime()) / 1000, 30 * 24 * 3600);
        // 获取二维码ticket
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(userUrl + "/s/sceneqr/nuanyou");
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(30000).setConnectTimeout(30000).setSocketTimeout(30000).build();

        List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
        formParams.add(new BasicNameValuePair("sceneid", subReceiptQrCode.getSceneId()));
        formParams.add(new BasicNameValuePair("name", "收银小票关注码"));
        formParams.add(new BasicNameValuePair("expires", expires.toString()));
        post.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
        post.setConfig(config);
        CloseableHttpResponse res = httpClient.execute(post);
        String resString = EntityUtils.toString(res.getEntity());
        httpClient.close();

        JSONObject jsonObj = JSONObject.parseObject(resString);
        JSONObject dataObj = jsonObj.getJSONObject("data");
        subReceiptQrCode.setTicket(dataObj.getString("ticket"));
        subReceiptQrCode.setUrl(dataObj.getString("url"));
    }

    /**
     * 查询国家参数
     *
     * @param countryId
     * @return
     * @throws Exception
     */
    public CountryVo queryCountryBaseInfo(String countryId) throws Exception {
        List<NameValuePair> params = new ArrayList<>();
        String url = commonUrl + "/country/queryCountryConfigInfo";
        URI uri = new URI(url);
        HttpPost post = new HttpPost(uri);

        Map<String, String> param = Maps.newHashMap();
        param.put("countryId", countryId);

        StringEntity StringEntity = new StringEntity(JSON.toJSONString(param));
        post.addHeader(HTTP.CONTENT_TYPE, "application/json");
        StringEntity.setContentEncoding("UTF-8");
        StringEntity.setContentType("application/json");
        post.setEntity(StringEntity);
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(post);
        String responseText = EntityUtils.toString(response.getEntity());
        System.out.println(responseText);
        Result apiResult = JSON.parseObject(responseText, Result.class);
        if (apiResult.getCode() == null || apiResult.getCode() != 0) {
            String msg = JSONObject.parseObject(responseText).getString("msg");
            throw new ResultException(apiResult.getCode(), msg);
        }
        CountryVo countryVo = JSONObject.parseObject(apiResult.getData().toString(), CountryVo.class);
        return countryVo;
    }

    /**
     * 生成推送文案
     *
     * @param qrCode
     * @param pushLinkUrl
     * @return
     */
    public String makePushContent(EntitySubReceiptQrCodeConfig qrCode, String pushLinkUrl) {
        return "<MsgType><![CDATA[" + qrCode.getPushType() + "]]></MsgType>"
                + "<ArticleCount>1</ArticleCount>"
                + "<Articles>"
                + "<item>"
                + "<Title><![CDATA[" + qrCode.getPushTitle() + "]]></Title>"
                + "<Description><![CDATA[" + qrCode.getPushDesc() + "]]></Description>"
                + "<PicUrl><![CDATA[" + qrCode.getPushImgUrl() + "]]></PicUrl>"
                + "<Url><![CDATA[" + (StringUtils.isEmpty(pushLinkUrl) ? qrCode.getPushLinkUrl() : pushLinkUrl) + "]]></Url>"
                + "</item>"
                + "</Articles>";
    }

    /**
     * 更新文案
     *
     * @param sceneId
     * @param subscribeReply
     * @param scanReply
     * @throws Exception
     */
    public void updatePushContent(String sceneId, String subscribeReply, String scanReply) throws Exception {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("sceneid", sceneId));
        params.add(new BasicNameValuePair("scanreply", scanReply));
        params.add(new BasicNameValuePair("subscribereply", subscribeReply));

        Utils.sign(params, PRIVATE_KEY);

        HttpPost post = new HttpPost(userUrl + "/s/nuanyou/saveScanReply/cms");
        post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
        try (CloseableHttpResponse response = HttpClientBuilder.create().build().execute(post)) {
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }

    /**
     * 获取商户城市配置
     *
     * @param item
     * @return
     */
    public JSONObject getMerchantSubReceiptByCityTemplate(EntityMerchant item) throws IOException {
        String countryCode = item.getDistrict().getCity().getCountry().getCode();
        String cityCode = item.getDistrict().getCity().getCode();
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(region).build();

        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest(
                    bucket, userSubReceiptTemplate + countryCode + "/" + cityCode + "/default" + ".json");

            S3Object object = amazonS3.getObject(getObjectRequest);
            return this.fromInputStreamToJson(object.getObjectContent());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取商户国家配置
     *
     * @param item
     * @return
     */
    public JSONObject getMerchantSubReceiptByCountryTemplate(EntityMerchant item) throws IOException {
        String countryCode = item.getDistrict().getCity().getCountry().getCode();

        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
        GetObjectRequest getObjectRequest = new GetObjectRequest(
                bucket, userSubReceiptTemplate + countryCode + "/default" + ".json");

        S3Object object = amazonS3.getObject(getObjectRequest);
        return this.fromInputStreamToJson(object.getObjectContent());
    }

    /**
     * 获取商户配置
     *
     * @param item
     * @return
     */
    public JSONObject getMerchantSubReceiptTemplate(EntityMerchant item) {
        EntityCity city = item.getDistrict().getCity();
        String cityCode = city.getCode();
        String countryCode = city.getCountry().getCode();

        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(region).build();

        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest(
                    bucket, userSubReceiptTemplate + countryCode + "/" + cityCode + "/" + item.getId() + ".json");

            S3Object object = amazonS3.getObject(getObjectRequest);
            return this.fromInputStreamToJson(object.getObjectContent());
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 上传商户小票打印单模板
     *
     * @param jsonStr
     * @param configName
     * @throws IOException
     */
    public void putMerchantConfig(String jsonStr, String configName) throws IOException {
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(region).build();

        InputStream inputStream = new ByteArrayInputStream(jsonStr.getBytes());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(inputStream.available());
        metadata.setContentType(String.valueOf(jsonStr.length()));

        amazonS3.putObject(new PutObjectRequest(bucket, userSubReceiptPrint + configName, inputStream, metadata));
    }

    /**
     * 更新收银小票关注码
     *
     * @param jsonObject
     * @param qrCodeUrl
     * @return
     */
    public String setSubReceiptQrCodeUrl(JSONObject jsonObject, String qrCodeUrl) {
        JSONArray footer = jsonObject.getJSONArray("footer");
        JSONObject footerObject = JSONObject.parseObject(footer.get(1).toString());
        JSONObject contentObject = footerObject.getJSONObject("content");

        String url = contentObject.getString("url");
        if (!StringUtils.isEmpty(url)) {//如果模板不为空， 则用原本URL
            return jsonObject.toJSONString();
        }

        contentObject.put("url", qrCodeUrl);
        footerObject.put("content", contentObject);
        footer.remove(1);
        footer.add(footerObject);
        return jsonObject.toJSONString();
    }

    /**
     * 文件流转为JSON对象
     *
     * @param input
     * @return
     * @throws IOException
     */
    public JSONObject fromInputStreamToJson(InputStream input) throws IOException {
        StringBuilder json = new StringBuilder();
        // Read one text line at a time and display.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String inputLine = null;
        while ((inputLine = reader.readLine()) != null) {
            json.append(inputLine);
        }
        JSONObject response = JSONObject.parseObject(json.toString());
        reader.close();
        return response;
    }


}
