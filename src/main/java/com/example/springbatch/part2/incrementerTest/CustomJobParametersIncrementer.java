package com.example.springbatch.part2.incrementerTest;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JobParametersIncrementer 만들어보기
 * 다시 실행하기 위한 목적
 * BATCH_JOB_EXECUTION_PARAMS안에 run.id와 value값이 계속 다르게 생성됨.
 * BATCH_JOB_INSTANCE의 job_key값이 다르게 생성됨
 */
public class CustomJobParametersIncrementer implements JobParametersIncrementer {

    static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-hhmmss");

    @Override
    public JobParameters getNext(JobParameters parameters) {

        String id = CustomJobParametersIncrementer.format.format(new Date());

        return new JobParametersBuilder().addString("run.id", id).toJobParameters();
    }

}