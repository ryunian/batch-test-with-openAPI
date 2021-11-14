package com.shoon.batch.config;

import com.shoon.batch.dto.HouseTradeDTO;
import com.shoon.batch.listener.HouseTradeJobListener;
import com.shoon.batch.listener.HouseTradeStepListener;
import com.shoon.batch.repository.entity.PropertyPrice;
import com.shoon.batch.repository.jpa.LawDongRepository;
import com.shoon.batch.repository.jpa.PropertyPriceRepository;
import com.shoon.batch.step.processor.OpenApiHouseProcessor;
import com.shoon.batch.step.reader.OpenApiHouseReader;
import com.shoon.batch.step.writer.OpenApiHouseWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchJobConfig {
    public static final int CHUNK_AND_PAGE_SIZE = 100;
    private final DataSource dataSource;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final HouseTradeJobListener realEstateTradeJobListener;
    private final HouseTradeStepListener realEstateTradeStepListener;
    private final LawDongRepository lawDongRepository;
    private final PropertyPriceRepository propertyPriceRepository;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Primary
    public JpaTransactionManager jpaTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    @StepScope
    ItemReader<List<HouseTradeDTO>> houseTradeReader() {
        return new OpenApiHouseReader(restTemplate(), lawDongRepository);
    }

    @Bean
    @StepScope
    ItemProcessor<List<HouseTradeDTO>, List<PropertyPrice>> houseTradeProcessor() {
        return new OpenApiHouseProcessor();
    }

    @Bean
    @StepScope
    ItemWriter<List<PropertyPrice>> houseTradeWriter() {
        return new OpenApiHouseWriter(propertyPriceRepository);
    }

    @Bean
    public Job HouseTradeJob() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(CHUNK_AND_PAGE_SIZE);
        threadPoolTaskExecutor.afterPropertiesSet();

        Flow splitFlow = new FlowBuilder<Flow>("HouseTradeStepSplitFlow")
                .split(threadPoolTaskExecutor)
                .add(new FlowBuilder<Flow>("HouseTradeStepFlow")
                        .start(HouseTradeStep())
                        .build()
                ).build();

        return jobBuilderFactory.get("국토부 전국 부동산 실거래가 데이터 수집")
                .listener(realEstateTradeJobListener)
                .start(splitFlow)
                .end()
                .build();
    }

    @Bean
    public Step HouseTradeStep() {
        return stepBuilderFactory.get("단독/가구 매매 실거래자료 수집 스텝")
                .transactionManager(jpaTransactionManager())
                .<List<HouseTradeDTO>, List<PropertyPrice>>chunk(1)
                .reader(houseTradeReader())
                .processor(houseTradeProcessor())
                .writer(houseTradeWriter())
                .listener(realEstateTradeStepListener)
                .build();
    }
}