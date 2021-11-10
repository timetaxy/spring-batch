package com.example.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 총 4개의 설정클래스를 실행시키며 스프링 배치의 모든 초기화 및 실행 구성이 이루어진다
 * BatchAutoConfiguration, SimpleBatchConfiguration, BatchConfiguerConfiguration
 * 빈으로 등록된 모든 job을 검색해서 초기화와 동시에 job을 수행하도록 구성됨
 */

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

}
