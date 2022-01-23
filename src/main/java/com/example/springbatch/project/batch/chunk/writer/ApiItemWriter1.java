package com.example.springbatch.project.batch.chunk.writer;

import com.example.springbatch.project.batch.domain.ApiRequestVO;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ApiItemWriter1 implements ItemWriter<ApiRequestVO> {

    @Override
    public void write(List<? extends ApiRequestVO> list) throws Exception {

    }
}
