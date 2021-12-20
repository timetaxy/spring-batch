package com.example.springbatch.part2.validatorTest;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

/**
 * JobParametersValidator 실습
 */
public class CustomJobParameterValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        if(parameters.getString("name") == null) {
            throw new JobParametersInvalidException("name parameters is not found");
        }
    }

}