package com.example.springbatch.part2.validatorTest;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Validator실습
 * 아래와 같이 벨리데이터를 생성하지 않으면 simpleJob의 상속관계인 AbstractJob 아래의 클래스가 생성됩니다.
 * private JobParametersValidator jobParametersValidator = new DefaultJobParametersValidator();
 *
 * 1. JobBulderHelper에 가장 먼저 저장 후 벨리데이터에 전달
 * 2. AbstractJob로 이동
 * 3. SimpleJobLauncher에 접근하여 벨리데이트 검사 ----- 제일 중요!!!
 * 3-1. job.getJobParametersValidator().validate(jobParameters);
 * 3-2. jobExecution = jobRepository.createJobExecution(job.getName(), jobParameters);
 *
 * date누락시 아래의 에러를 발견할 수 있음 (DefaultJobParametersValidator) (88라인)
 * The JobParameters do not contain required keys: [date]
 *
 * 최종 : BATCH_JOB_EXECUTION_PARAMS 에 적재된 파라미터 값을 확인할 수 있다.
 */
@Configuration
@RequiredArgsConstructor
public class ValidatorConfigurationTest {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                .start(step1())
                .next(step2())
                .next(step3())
//                .validator(new CustomJobParameterValidator())
                .validator(new DefaultJobParametersValidator(new String[]{"name", "date",}, new String[]{"count"}))
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
                        System.out.println("step3 was executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}