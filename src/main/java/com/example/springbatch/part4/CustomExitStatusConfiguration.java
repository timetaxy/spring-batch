package com.example.springbatch.part4;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ExitStatus
 * ExitStatus 에 존재하지 않는 exitCode 를 새롭게 정의해서 설정
 * StepExecutionListener 의 afterStep() 메서드에서 Custom exitCode 생성 후 새로운 ExitStatus 반환
 * Step 실행 후 완료 시점에서 현재 exitCode 를 사용자 정의 exitCode 로 수정할 수 있음
 */
@Configuration
@RequiredArgsConstructor
public class CustomExitStatusConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return this.jobBuilderFactory.get("batchJob")
                .start(step1())
                    .on("FAILED")// FAILED 조건 만족
                    .to(step2())
                    .on("PASS")
                    .stop()
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("-> step1 has executed");
                    contribution.getStepExecution().setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("-> step2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .listener(new PassCheckingListener()) // 사용자 정의 PASS
                .build();
    }

}