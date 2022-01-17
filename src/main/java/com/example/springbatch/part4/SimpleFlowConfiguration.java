//package com.example.springbatch.part4;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.job.builder.FlowBuilder;
//import org.springframework.batch.core.job.flow.Flow;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Flow 개념, State개념, Flow안에 중첩 설정
// * flow 전략
// * COMPLETED : 1,2,3,4,5,6
// * FAILED : 1,2에서 실패, 3, 4
// */
//@Configuration
//@RequiredArgsConstructor
//public class SimpleFlowConfiguration {
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//
//    @Bean Job job() {
//        return jobBuilderFactory.get("batchJob")
//                .incrementer(new RunIdIncrementer())
//                .start(flow1()) // flow종료는 end가 필요함 (SimpleFlow객체 생성)
//                    .on("COMPLETED") //성공하면 flow2
//                    .to(flow2())
//                .from(flow1())
//                    .on("FAILED") // 실패하면 flow3
//                    .to(flow3())
//                .end() // (SimpleFlow객체 생성)
//                .build();
//    }
//
//    @Bean
//    public Flow flow1() {
//        FlowBuilder<Flow> builder = new FlowBuilder<>("flow1");
//        builder.start(step1())
//                .next(step2())
//                .end();
//
//        return builder.build();
//    }
//
//    @Bean
//    public Flow flow2() {
//        FlowBuilder<Flow> builder = new FlowBuilder<>("flow2");
//        builder.start(flow3()) // flow 중첩 설정
//                .next(step5())
//                .next(step6())
//                .end();
//
//        return builder.build();
//    }
//
//    @Bean
//    public Flow flow3() {
//        FlowBuilder<Flow> builder = new FlowBuilder<>("flow3");
//        builder.start(step3())
//                .next(step4())
//                .end();
//
//        return builder.build();
//    }
//
//    @Bean
//    public Step step1() {
//        return stepBuilderFactory.get("step1")
//                .tasklet((contribution, chunkContext) -> {
//                    System.out.println("-> step1 has executed");
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
//
//    @Bean
//    public Step step2() {
//        return stepBuilderFactory.get("step2")
//                .tasklet((contribution, chunkContext) -> {
//                    System.out.println("-> step2 has executed");
//                    throw new RuntimeException("step2 was failed");
////                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
//    @Bean
//    public Step step3() {
//        return stepBuilderFactory.get("step3")
//                .tasklet((contribution, chunkContext) -> {
//                    System.out.println("-> step3 has executed");
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
//
//    @Bean
//    public Step step4() {
//        return stepBuilderFactory.get("step4")
//                .tasklet((contribution, chunkContext) -> {
//                    System.out.println("-> step4 has executed");
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
//
//    @Bean
//    public Step step5() {
//        return stepBuilderFactory.get("step5")
//                .tasklet((contribution, chunkContext) -> {
//                    System.out.println("-> step5 has executed");
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
//    @Bean
//    public Step step6() {
//        return stepBuilderFactory.get("step6")
//                .tasklet((contribution, chunkContext) -> {
//                    System.out.println("-> step6 has executed");
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
//}