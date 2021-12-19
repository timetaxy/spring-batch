package com.example.springbatch.part2.simpleJobTest;

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

/**
 * SimpleJob은 오로지 STEP으로만 구성할 수 있다.
 *
 * step이 이루어질때 아래와 같이 이루어 집니다.
 * 1. JobBulider가 SimpleJobBuilder을 생성 함.
 * 1-1. JobBulider가 JobBuilderHelper를 상속 받고 있음.
 * 2. JobBuilderHelper가 CommonJobProperties를 가지고 있음.
 * 중요!!!! : CommonJobProperties는 api에서 설정한 항목들과 속성들을 제어 할수 있습니다.
 *
 * build가 끝나면 아래와 같이 진행됩니다.
 * simpleJobBuilder가 내부적으로 진행
 * 1. step이 steps를 통해 list에 추가 됨니다.
 *
 * 1. SimpleJobLauncher.java 시작
 * - SimpleJobLauncher job.execute(jobExecution); 에 디버깅 시작
 * - step에 3가지 아래의 3가지를 list구조로 가지고 있음.
 * 2. SimpleJob : SimpleJobLauncher가 SimpleJob을 실행시키는 시점 (3개의 step)
 * -- 68 라인 public void setSteps(List<Step> steps)
 *ㅕㅣ.
 */
@Configuration
@RequiredArgsConstructor
public class simpleJobTest {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                .start(step1())
                .next(step2())
                .next(step3())
                .incrementer(new RunIdIncrementer())
                .validator(new JobParametersValidator() {
                    @Override
                    public void validate(JobParameters parameters) throws JobParametersInvalidException {

                    }
                })
                .preventRestart()
                .listener(new JobParametersValidator() {
                    @Override
                    public void validate(JobParameters parameters) throws JobParametersInvalidException {

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
                        System.out.println("step1 was executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step2 was executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }


    /**
     * BATCH_JOB_EXECUTION에서  BatchStatus, ExitStatus 값을 설정해 줄수 있다.
     * @return
     */
    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        chunkContext.getStepContext().getStepExecution().setStatus(BatchStatus.FAILED);
                        contribution.setExitStatus(ExitStatus.STOPPED);
                        System.out.println("step3 was executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}