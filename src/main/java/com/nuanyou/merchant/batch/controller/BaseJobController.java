package com.nuanyou.merchant.batch.controller;

import com.nuanyou.merchant.batch.common.Result;
import com.nuanyou.merchant.batch.common.ResultCode;
import com.nuanyou.merchant.batch.common.ResultException;
import com.nuanyou.merchant.batch.utils.DateUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by mylon.sun on 2018/2/9.
 */
public class BaseJobController {

    @Resource(name = "subReceiptPrintsJob")
    protected Job subRptPrintsJob;

    @Resource(name = "subReceiptPrintJob")
    protected Job subRptPrintJob;

    @Resource(name = "subReceiptCodesJob")
    protected Job subRptCodesJob;

    @Resource(name = "subReceiptCodeJob")
    protected Job subRptCodeJob;

    @Resource(name = "subReceiptCodeExpiresJob")
    protected Job subRptCodeExpiresJob;

    @Resource(name = "subReceiptCodeExpireJob")
    protected Job subRptCodeExpireJob;

    @Resource(name = "subReceiptCodeDocsJob")
    protected Job subRptCodeDocsJob;

    @Resource(name = "subReceiptCodeDocJob")
    protected Job subRptCodeDocJob;

    @Autowired
    protected JobLauncher jobLauncher;

    @Autowired
    protected JobOperator jobOperator;

    protected String defaultDayParam = DateUtils.getDefaultFormat();
    protected String defaultDateParam = DateUtils.format(new Date());

    /**
     * 发起任务
     *
     * @param dateStr
     * @param param_name
     * @param param_val
     * @param job
     * @return
     */
    protected Result launchJob(String dateStr, String param_name, Long param_val, Job job) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("date", dateStr)
                    .addLong(param_name, param_val)
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);
            return new Result();
        } catch (JobExecutionAlreadyRunningException e) {
            throw new ResultException(ResultCode.JobExecutionAlreadyRunning);
        } catch (JobRestartException e) {
            throw new ResultException(ResultCode.JobRestart);
        } catch (JobInstanceAlreadyCompleteException e) {
            throw new ResultException(ResultCode.JobInstanceAlreadyComplete);
        } catch (JobParametersInvalidException e) {
            throw new ResultException(ResultCode.JobParametersInvalid);
        }
    }

}
