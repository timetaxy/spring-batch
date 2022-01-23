package com.example.springbatch.project.batch.classifier;

import com.example.springbatch.project.batch.domain.ApiRequestVO;
import com.example.springbatch.project.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

import java.util.HashMap;
import java.util.Map;

/**
 * c : 클래스타입, T: 반환타입
 * @param <C>
 * @param <T>
 */
public class ProcessClassifier<C, T> implements Classifier<C, T> {

    private Map<String, ItemProcessor<ProductVO, ApiRequestVO>> processorMap = new HashMap<>();

    @Override
    public T classify(C Classifiable) {

        return (T)processorMap.get(((ProductVO)Classifiable).getType()); // 1,2,3번 순으로 반환

    }

    public void setProcessorMap(Map<String, ItemProcessor<ProductVO, ApiRequestVO>> processorMap) {
        this.processorMap = processorMap;
    }
}