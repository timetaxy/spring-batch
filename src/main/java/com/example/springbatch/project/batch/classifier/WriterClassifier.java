package com.example.springbatch.project.batch.classifier;

import com.example.springbatch.project.batch.domain.ApiRequestVO;
import com.example.springbatch.project.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

import java.util.HashMap;
import java.util.Map;

/**
 * c : 클래스타입, T: 반환타입
 * @param <C>
 * @param <T>
 */
public class WriterClassifier<C, T> implements Classifier<C, T> {

    private Map<String, ItemWriter<ApiRequestVO>> writerMap = new HashMap<>();

    @Override
    public T classify(C Classifiable) {

        return (T)writerMap.get(((ApiRequestVO)Classifiable).getProductVO().getType()); // 1,2,3번 순으로 반환

    }

    public void setWriterMapMap(Map<String, ItemWriter<ApiRequestVO>> writerMapMap) {
        this.writerMap = writerMap;
    }
}