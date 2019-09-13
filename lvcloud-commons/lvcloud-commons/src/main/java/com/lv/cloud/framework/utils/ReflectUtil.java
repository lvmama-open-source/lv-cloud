package com.lv.cloud.framework.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ReflectUtil {
	/**
     * 获取指定对象的指定属性
     * 
     * @param obj 指定对象
     * @param fieldName 指定属性名称
     * @return 指定属性
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Object result = null;
        Field field = ReflectUtil.getField(obj, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                result = field.get(obj);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取指定对象里面的指定属性对象
     * 
     * @param obj 目标对象
     * @param fieldName 指定属性名称
     * @return 属性对象
     */
    private static Field getField(Object obj, String fieldName) {
        Field field = null;
        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                // do nothing
            }
        }
        return field;
    }

    /**
     * 设置指定对象的指定属性值
     * 
     * @param obj 指定对象
     * @param fieldName 指定属性
     * @param fieldValue 指定属性值
     */
    public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
        Field field = ReflectUtil.getField(obj, fieldName);
        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(obj, fieldValue);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 获取一个Class上所有的属性，包含父类属性
     * @param type
     * @return
     */
    public static List<Field> getAllFileds(Class<?> type){
    	List<Field> fieldList = new ArrayList<>();
    	//当父类为null的时候说明到达了最上层的父类(Object类).
    	while (type != null && !type.getName().toLowerCase().equals("java.lang.object")) {
    	      fieldList.addAll(Arrays.asList(type .getDeclaredFields()));
    	      type = type.getSuperclass(); //得到父类,然后赋给自己
    	}
    	for (Field f : fieldList) {
    	    log.debug("getAllFields","getFields---"+f.getName());
    	}
    	return fieldList;
    }
    
    @SuppressWarnings("unchecked")  
    public static Class<Object> getSuperClassGenricType(final Class<?> clazz, final int index) {  
          
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。  
        Type genType = clazz.getGenericSuperclass();  
  
        if (!(genType instanceof ParameterizedType)) {  
           return Object.class;  
        }  
        //返回表示此类型实际类型参数的 Type 对象的数组。  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
  
        if (index >= params.length || index < 0) {  
                     return Object.class;  
        }  
        if (!(params[index] instanceof Class)) {  
              return Object.class;  
        }  
  
        return (Class<Object>) params[index];  
    }  
}
