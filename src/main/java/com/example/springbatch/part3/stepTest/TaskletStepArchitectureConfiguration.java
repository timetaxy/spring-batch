package com.example.springbatch.part3.stepTest;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TaskletStepArchitectureConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer()) // 동일 파라미터인데 다시 실행하고 싶을때 사용하라는 의미로 RunIdIncrementer를 제공
                .start(step1())
                .next(step2())
                .listener(new StepExecutionListener() {
                    // AbstractStep 시점 204라인
                    @Override
                    public void beforeStep(StepExecution stepExecution) {

                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        return null;
                    }
                })
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("stepContribution = " + contribution + ", ChunkContext = " + chunkContext);
                        return RepeatStatus.FINISHED;
                    }
                })
                .allowStartIfComplete(true) // job이 성공하거나 실패하거나 무조건 실행되게함 BATCH_STEP_EXECUTION에 저장
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("stepContribution = " + contribution + ", ChunkContext = " + chunkContext);
                        return RepeatStatus.FINISHED;
                    }
                })
                .startLimit(3)  // step 3번째까지 실행되고 네번째부터 발생함 (BATCH_JOB_EXECUTION에는 저장되고 아래의 메시지를 출력하고, BATCH_STEP_EXECUTION에는 저장안됨
                // org.springframework.batch.core.StartLimitExceededException: Maximum start limit exceeded for step: step2StartMax: 3
                .build();
    }

}