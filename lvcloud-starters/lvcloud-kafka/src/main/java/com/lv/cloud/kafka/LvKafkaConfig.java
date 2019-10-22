package com.lv.cloud.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

/**
 * @author xiaoyulin
 * @description
 * @date 2019-10-10
 */
@Configuration
@Import({KafkaAutoConfiguration.class})
public class LvKafkaConfig {

    @Bean
    @Autowired
    public ConcurrentKafkaListenerContainerFactory<?, ?> batchKafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactory<?, ?> factory) {
        factory.setBatchListener(true);
        factory.setMessageConverter(batchConverter());
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean
    public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public BatchMessagingMessageConverter batchConverter() {
        return new BatchMessagingMessageConverter(converter());
    }

}
