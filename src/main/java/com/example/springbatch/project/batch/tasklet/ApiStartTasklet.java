package com.example.springbatch.project.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

// 리스너를 활용할 수 있지만 시작,종료를 확인하기 위해
@Component
public class ApiStartTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        System.out.println("");
        System.out.println(">> ApiStartTasklet is started");
        System.out.println("");

        return RepeatStatus.FINISHED;
    }
}
