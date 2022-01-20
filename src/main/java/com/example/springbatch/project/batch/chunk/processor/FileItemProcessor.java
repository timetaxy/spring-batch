package com.example.springbatch.project.batch.chunk.processor;

import com.example.springbatch.project.batch.domain.Product;
import com.example.springbatch.project.batch.domain.ProductVO;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductVO, Product> {
    @Override
    public Product process(ProductVO item) throws Exception {

        // 도메인기준 파일로 부터 실제 읽어오기
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(item, Product.class);

        return product;
    }
}
