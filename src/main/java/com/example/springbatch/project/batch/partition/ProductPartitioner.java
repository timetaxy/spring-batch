package com.example.springbatch.project.batch.partition;

import com.example.springbatch.project.batch.domain.ProductVO;
import com.example.springbatch.project.batch.job.api.QueryGenerator;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

// 멀티쓰레드 환경
public class ProductPartitioner implements Partitioner {

    // DataSource 필요
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        // 파티셔닝 강의 필요 ExecutionContext

        // 제품유형의 개수대로 생성 (3개)
        ProductVO[] productList = QueryGenerator.getProductList(dataSource);
        Map<String, ExecutionContext> result = new HashMap<>();

        int number = 0;

        for (int i=0; i< productList.length; i++) {
            ExecutionContext value = new ExecutionContext();

            result.put("partition" + number, value);
            value.put("product", productList[i]); // i번째 바인딩

            number++;
        }

        return result;
    }
}
