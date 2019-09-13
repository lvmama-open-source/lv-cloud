package com.lv.cloud.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;
import java.util.Properties;

@Slf4j
public class YamlUtils {

	private final static ClassPathResource[] empty = {};

    public static Map<String, Object> yaml2Map(String... yamlSource) {
        try {
            YamlMapFactoryBean yaml = new YamlMapFactoryBean();
            yaml.setResources(getClassPathResources(yamlSource));
            return yaml.getObject();
        } catch (Exception e) {
            log.error("Cannot read yaml", e);
            return null;
        }
    }
    
    private static ClassPathResource[] getClassPathResources(String[] arg){
    	if(arg == null){
    		return empty;
    	}else{
    		ClassPathResource[] result = new ClassPathResource[arg.length];
    		for (int i = 0; i < result.length; i++) {
    			result[i]=new ClassPathResource(arg[i]);
			}
    		return result;
    	}
    }

    public static Properties yaml2Properties(String... yamlSource) {
        try {
            YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
            yaml.setResources(getClassPathResources(yamlSource));
            return yaml.getObject();
        } catch (Exception e) {
        	log.error("Cannot read yaml", e);
            return null;
        }
    }
}
