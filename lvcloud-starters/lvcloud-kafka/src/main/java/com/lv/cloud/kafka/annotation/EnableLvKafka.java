package com.lv.cloud.kafka.annotation;

import com.lv.cloud.kafka.LvKafkaConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用spring kafka
 * @author xiaoyulin
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LvKafkaConfig.class)
public @interface EnableLvKafka {
	
	

}
