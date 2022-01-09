package com.example.springbatch.part4;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class PassCheckingListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        String exitCode = stepExecution.getExitStatus().getExitCode();

        // 실행코드가 FAILED가 아니면 PASS "COMPLETED"로 들어옴
        if(!exitCode.equals(ExitStatus.FAILED.getExitCode())) {
            return new ExitStatus("PASS");
        }

        return null;
    }
}