package com.nuanyou.merchant.batch.controller;

import com.nuanyou.merchant.batch.common.Result;
import com.nuanyou.merchant.batch.common.ResultCode;
import com.nuanyou.merchant.batch.common.ResultException;
import com.nuanyou.merchant.batch.utils.DateUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping("batch")
public class BatchController extends BaseJobController{

    /**
     * 根据国家ID批量更新小票S3模板
     *
     * @param countryId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "subReceiptPrintsJob")
    public Result subReceiptPrintJob(@RequestParam("countryId") Long countryId) {
        return this.launchJob(defaultDayParam, "countryId", countryId, subRptPrintsJob);
    }

    /**
     * 根据商户ID更新单个商户收银小票S3模板
     *
     * @param merchantId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "subReceiptPrintJob")
    public Result merchantSubReceiptPrintJob(@RequestParam("mchId") Long merchantId) {
        return this.launchJob(defaultDateParam, "merchantId", merchantId, subRptPrintJob);
    }

    /**
     * 根据国家ID生成所有商户的小票关注码
     *
     * @param countryId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "subReceiptCodesJob")
    public Result subReceiptCodeJob(
            @RequestParam("countryId") Long countryId
    ) {
        return this.launchJob(defaultDayParam, "countryId", countryId, subRptCodesJob);
    }

    /**
     * 根据商户ID生成商户的小票关注码
     *
     * @param merchantId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "subReceiptCodeJob")
    public Result merchantSubReceiptCodeJob(
            @RequestParam("mchId") Long merchantId
    ) {
        return this.launchJob(defaultDateParam, "merchantId", merchantId, subRptCodeJob);
    }

    /**
     * 根据国家ID更新所有商户的小票关注码的过期时间
     *
     * @param countryId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "subReceiptCodeExpiresJob")
    public Result subReceiptCodeRefreshExpireTimeJob(
            @RequestParam("countryId") Long countryId
    ) {
        return this.launchJob(defaultDayParam, "countryId", countryId, subRptCodeExpiresJob);
    }

    /**
     * 根据商户ID更新单个商户的小票关注码的过期时间
     *
     * @param merchantId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "subReceiptCodeExpireJob")
    public Result merchantSubReceiptCodeRefreshExpireTimeJob(
            @RequestParam("mchId") Long merchantId
    ) {
        return this.launchJob(defaultDateParam, "merchantId", merchantId, subRptCodeExpireJob);
    }

    /**
     * 根据国家ID更新所有商户的小票关注码的推送文案
     *
     * @param countryId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "subReceiptCodeDocsJob")
    public Result subReceiptCodeRefreshDocJob(
            @RequestParam("countryId") Long countryId
    ) {
        return this.launchJob(defaultDayParam, "countryId", countryId, subRptCodeDocsJob);
    }

    /**
     * 根据商户ID更新单个商户的小票关注码的推送文案
     *
     * @param merchantId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "subReceiptCodeDocJob")
    public Result merchantSubReceiptCodeRefreshDocJobJob(
            @RequestParam("mchId") Long merchantId
    ) {
        return this.launchJob(defaultDateParam, "merchantId", merchantId, subRptCodeDocJob);
    }

    /**
     * Send a stop signal to the {@link JobExecution} with the supplied id.
     *
     * @param executionId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "_stop")
    public Result _stop(
            @RequestParam("executionId") Long executionId
    ) {
        try {
            boolean result = jobOperator.stop(executionId);
            return new Result(result);
        } catch (NoSuchJobExecutionException e) {
            throw new ResultException(ResultCode.NoSuchJobExecution);
        } catch (JobExecutionNotRunningException e) {
            throw new ResultException(ResultCode.JobExecutionNotRunning);
        }
    }

    /**
     * Restart a failed or stopped {@link JobExecution}
     *
     * @param executionId
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "_restart")
    public Result _restart(
            @RequestParam("executionId") Long executionId
    ) {
        try {
            jobOperator.restart(executionId);
            return new Result();
        } catch (JobInstanceAlreadyCompleteException e) {
            throw new ResultException(ResultCode.JobInstanceAlreadyComplete);
        } catch (NoSuchJobExecutionException e) {
            throw new ResultException(ResultCode.NoSuchJobExecution);
        } catch (NoSuchJobException e) {
            throw new ResultException(ResultCode.NoSuchJobExecution);
        } catch (JobRestartException e) {
            throw new ResultException(ResultCode.JobRestart);
        } catch (JobParametersInvalidException e) {
            throw new ResultException(ResultCode.JobParametersInvalid);
        }
    }

}


