package com.lv.cloud.framework.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoyulin
 * @description 构建Spring context holder
 * @date 2019-09-13
 */
@Configuration
public class SpringContextHolderConfig {

    @Bean
    @ConditionalOnMissingBean
    public SpringContextHolder springContextHolder(){
        return new SpringContextHolder();
    }

}
