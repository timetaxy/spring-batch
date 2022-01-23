package com.example.springbatch.project.service;

import com.example.springbatch.project.batch.domain.ApiInfo;
import com.example.springbatch.project.batch.domain.ApiRequestVO;
import com.example.springbatch.project.batch.domain.ApiResponseVO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

public abstract class AbstractApiService {

    // itemWriter에서 받은값 전달
    public ApiResponseVO service(List<? extends ApiRequestVO> apiRequest) {

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        // restTemplate를 통해 예외처리 발생시 아래 로직 구현
        RestTemplate restTemplate = restTemplateBuilder.errorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        }).build();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        // header 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // url,정보 정보 전달
        ApiInfo apiInfo = ApiInfo.builder().apiRequestList(apiRequest).build();

        // 통신
        return doApiService(restTemplate, apiInfo);
    }

    // 추상 클래스 생성
    protected abstract ApiResponseVO doApiService(RestTemplate restTemplate, ApiInfo apiInfo);
}