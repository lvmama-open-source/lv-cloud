package com.lv.cloud.framework.config;

import com.lv.cloud.framework.annotation.ServiceAdapterConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Application Context holder
 */
@Slf4j
public class SpringContextHolder implements ApplicationContextAware {

	/**
     * 以静态变量保存ApplicationContext,可在任意代码中取出ApplicaitonContext.
     */
    private static ApplicationContext context;

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     */
    public void setApplicationContext(ApplicationContext context) {
        SpringContextHolder.context = context;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
    	return (T) context.getBean(name);
    }
    
    public static <T> T getBean(Class<T> classT){
    	return context.getBean(classT);
    }
    
    public static <T> T getBean(String name,Class<T> classT){
    	return context.getBean(name,classT);
    }

    /**
     * 获取category服务
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> Map<Long, T> getCategoryBeansOfType(Class<T> type) throws BeansException {
        Map<Long, T> categoryBeans = new HashMap<>();
        Map<String, T> result = context.getBeansOfType(type);
        if(result != null){
            log.info("getCategoryBeansOfType:{},size:{}" , type, result.size());
            Iterator<String> it = result.keySet().iterator();
            String key = null;
            while (it.hasNext()){
                key = it.next();
                T t = result.get(key);
                ServiceAdapterConfig serviceAdapterConfig =
                        t.getClass().getAnnotation(ServiceAdapterConfig.class);
                if(serviceAdapterConfig != null) {
                    long[] categoryIds = serviceAdapterConfig.categoryId();
                    log.info("Category Beans key:{},category:{}" , key, categoryIds);
                    for (long categoryId : categoryIds) {
                        categoryBeans.put(categoryId, t);
                    }
                }
            }
        }
        return categoryBeans;
    }
}
