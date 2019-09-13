package com.lv.cloud.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * 品类差异化服务工厂
 *
 */
public class BusinessAdapterFactory<T> {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessAdapterFactory.class);

    /**
     * 服务Map（key：categoryId,value：bean）
     */
    private volatile Map<Long, T> serviceKeyMap = null;

    protected T getCategoryBeansService(Long categoryId) {
        if (categoryId == null) {
            return null;
        }

        if(serviceKeyMap == null || serviceKeyMap.size() < 1){
            synchronized(this){
                if(serviceKeyMap == null || serviceKeyMap.size() < 1){
                    init();
                }
            }
        }
        T categoryBeansService = serviceKeyMap.get(categoryId);
        LOG.info("categoryId:" + categoryId + ",categoryBeansService:" + categoryBeansService);
        return categoryBeansService;
    }


    private void init(){
        serviceKeyMap = SpringContextHolder.getCategoryBeansOfType(this.getTClass());
        LOG.info("serviceKeyMap.size:" + (serviceKeyMap == null?0:serviceKeyMap.size()));
    }

    public Class<T> getTClass() {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

}
