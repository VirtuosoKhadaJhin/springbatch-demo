package com.nuanyou.merchant.batch.transformer;

import com.nuanyou.merchant.batch.entity.EntityMerchant;
import com.nuanyou.merchant.batch.utils.DateUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;

import java.util.Date;

public class SubReceiptRequestTransformer {

    private Job job;

    public void setJob(Job job) {
        this.job = job;
    }

    @Transformer
    public JobLaunchRequest toRequest(Message<EntityMerchant> message) {
        EntityMerchant merchant = message.getPayload();
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

        jobParametersBuilder.addString("date", DateUtils.format(new Date()));
        jobParametersBuilder.addLong("merchantId", merchant.getId());

        return new JobLaunchRequest(job, jobParametersBuilder.toJobParameters());
    }
}
