package com.example.springbatch.project.batch.job.file;

import com.example.springbatch.project.batch.chunk.processor.FileItemProcessor;
import com.example.springbatch.project.batch.domain.Product;
import com.example.springbatch.project.batch.domain.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class fileJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory; // 파일로 부터 읽어오기 db에 저장

    @Bean
    public Job fileJob() {
        return jobBuilderFactory.get("fileJob")
                .start(fileStep())
                .build();
    }

    @Bean
    public Step fileStep() {
        return stepBuilderFactory.get("fileStep")
                .<ProductVO, Product>chunk(10) // 읽어들일땐 도메인 객체, 사용할땐 엔티티 객체
                .reader(fileItemReader(null))
                .processor(fileItemProcessor())
                .writer(fileItemWriter())
                .build();
    }

    /**
     * 파일 생성
     * @param requestDate
     * @return
     *
     * EL1008E: Property or field 'jobParameters' cannot be found on object of type 오류
     * 원인 : jobParameter를 사용하는데 @StepScope or @JobScope를 사용하지 않아서 발생한 오류
     * 해결 : jobParameters 사용하는 부분에 @StepScope or @JobScope 기입해준다.
     *
     */
    @Bean
    @StepScope
    public FlatFileItemReader<ProductVO> fileItemReader(@Value("#{jobParameters['requestDate']}") String requestDate) {
        return new FlatFileItemReaderBuilder<ProductVO>()
                .name("flatFile")
                .resource(new ClassPathResource("product_" + requestDate + ".csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(ProductVO.class)
                .linesToSkip(1) // 첫번째줄 스킵 (헤더)
                .delimited().delimiter(",")
                .names("id", "name", "price", "type")
                .build();
    }

    /**
     * 파일 컨트롤
     * @return
     */
    @Bean
    public ItemProcessor<ProductVO, Product> fileItemProcessor() {
        return new FileItemProcessor();
    }

    /**
     * 파일로 부터 읽어오기 db에 저장
     * @return
     */
    @Bean
    public ItemWriter<Product> fileItemWriter() {
        return new JpaItemWriterBuilder<Product>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }
}
