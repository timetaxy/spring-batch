package com.example.springbatch.part4;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TransitionFlowJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
     * 정상 실행
     * 총 3개의 플로우
     * output : step1 -> step3 -> step4 (가운데 flow만 성공)
     *
     * 이슈발생
     * start 구문쪽을 성공시키리면 에러를 발생시켜야 함
     * -> step1은 contribution.setExitStatus(ExitStatus.FAILED) 를 통해 일치해서
     * 실패를 발생 시키지만 step2는 조건을 만족하지 않아 pass
     * from절에 step2()는 조건에 상관없이 실행을 시키기 때문에 step5()를 발생
     * output : step1 -> step2 -> step5
     * @return
     */
    @Bean
    public Job batchJob() {
        return this.jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1()) // SimpleJobBuilder가 on을 통해 생성됨(패턴 확인)
                    .on("FAILED") // 실패하면 step2로 갈것
                    .to(step2())
                    .on("FAILED") // FAIL이면 중단
                    .stop() // 여기까지가 하나의 Flow
                .from(step1())
                    .on("*")// FAILD가 우선순위 *는 후순위
                    .to(step3())
                    .next(step4())
                .from(step2())
                    .on("*")
                    .to(step5())
                .end()// simpleFlow객체를 통해 저장
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step1 was executed");
                    contribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                })
                .build();

    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step2 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();

    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step3 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();

    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step4 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();

    }

    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step5 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();

    }
}