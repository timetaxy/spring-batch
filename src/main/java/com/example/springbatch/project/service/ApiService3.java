package com.example.springbatch.project.service;

import com.example.springbatch.project.batch.domain.ApiInfo;
import com.example.springbatch.project.batch.domain.ApiResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService3 extends AbstractApiService{

    @Override
    protected ApiResponseVO doApiService(RestTemplate restTemplate, ApiInfo apiInfo) {

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://losthost:8083/api/product/3", apiInfo, String.class);
        int statusCodeValue = responseEntity.getStatusCodeValue();

        ApiResponseVO apiResponseVO = ApiResponseVO.builder()
                .status(statusCodeValue)
                .msg(responseEntity.getBody()).build();

        return apiResponseVO;
    }
}