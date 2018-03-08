package com.nuanyou.merchant.batch.controller;

import com.alibaba.fastjson.JSONObject;
import com.nuanyou.merchant.batch.common.Result;
import com.nuanyou.merchant.batch.common.ResultCode;
import com.nuanyou.merchant.batch.dao.SubReceiptQrCodeDao;
import com.nuanyou.merchant.batch.entity.EntitySubReceiptQrCode;
import com.nuanyou.merchant.batch.service.SubReceiptPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by mylon.sun on 2018/2/8.
 */
@Controller
@RequestMapping("batch/view")
public class BatchViewController {

    @Autowired
    private SubReceiptPrintService subReceiptPrintService;

    @Autowired
    private SubReceiptQrCodeDao subReceiptQrCodeDao;

    @ResponseBody
    @RequestMapping(path = "result/_subReceiptPrintJob")
    public Result _subReceiptPrintJob(@RequestParam("mchId") Long merchantId) {
        try {
            JSONObject printView = subReceiptPrintService.printView(merchantId);
            return new Result(printView);
        } catch (Exception e) {
            return new Result(ResultCode.FAIL);
        }
    }

    @ResponseBody
    @RequestMapping(path = "result/_subReceiptCodeJob")
    public Result _subReceiptCodeJob(@RequestParam("mchId") Long merchantId) {
        try {
            EntitySubReceiptQrCode qrCode = subReceiptQrCodeDao.findTopByMchIdAndDelFlag(merchantId, Boolean.FALSE);
            return new Result(qrCode);
        } catch (Exception e) {
            return new Result(ResultCode.FAIL);
        }
    }

    @ResponseBody
    @RequestMapping(path = "result/_subReceiptCodeExpireJob")
    public Result _subReceiptCodeExpireJob(@RequestParam("mchId") Long merchantId) {
        try {
            EntitySubReceiptQrCode qrCode = subReceiptQrCodeDao.findTopByMchIdAndDelFlag(merchantId, Boolean.FALSE);
            return new Result(qrCode);
        } catch (Exception e) {
            return new Result(ResultCode.FAIL);
        }
    }

    @ResponseBody
    @RequestMapping(path = "result/_subReceiptCodeDocJob")
    public Result _subReceiptCodeDocJob(@RequestParam("mchId") Long merchantId) {
        try {
            EntitySubReceiptQrCode qrCode = subReceiptQrCodeDao.findTopByMchIdAndDelFlag(merchantId, Boolean.FALSE);
            return new Result(qrCode);
        } catch (Exception e) {
            return new Result(ResultCode.FAIL);
        }
    }

}
