package com.nuanyou.merchant.batch.scheduler;

import com.nuanyou.merchant.batch.dao.CountryDao;
import com.nuanyou.merchant.batch.entity.EntityCountry;
import com.nuanyou.merchant.batch.utils.DateUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class SubReceiptCountryScheduler extends Thread {

    private Job job;

    private String date;

    private Long countryId;

    @Value("${jobCountries}")
    private String jobCountries;

    @Autowired
    private JobLauncher jobLauncher;

    @Override
    public void run() {
        JobParameters param = new JobParametersBuilder()
                .addString("date", date)
                .addLong("countryId", countryId)
                .toJobParameters();
        try {
            jobLauncher.run(job, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startJob() throws InterruptedException {
        String date = DateUtils.getDefaultFormat();
        String[] jobCountryList = jobCountries.split(",");
        for (String jobCountry : jobCountryList) {
            new SubReceiptCountryScheduler(jobLauncher, job, date, Long.parseLong(jobCountry)).run();
            Thread.sleep(5000);
        }
    }

    public SubReceiptCountryScheduler() {
        super();
    }

    public SubReceiptCountryScheduler(JobLauncher jobLauncher, Job job, String date, Long countryId) {
        this.job = job;
        this.date = date;
        this.countryId = countryId;
        this.jobLauncher = jobLauncher;
    }

    public void setJob(Job job) {
        this.job = job;
    }

}
