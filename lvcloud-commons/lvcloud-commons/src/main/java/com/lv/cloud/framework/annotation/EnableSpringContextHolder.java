package com.lv.cloud.framework.annotation;

import com.lv.cloud.framework.config.SpringContextHolderConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用JedisCluster
 * @author xiaoyulin
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SpringContextHolderConfig.class)
public @interface EnableSpringContextHolder {
	
	

}
