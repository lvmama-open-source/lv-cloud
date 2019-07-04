package com.lv.cloud.beans;

import com.lv.cloud.exception.CommonBeansException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 深度copy用
 * @author xiaoyulin
 *
 */
public class EnhanceBeanUtils extends BeanUtils {
	
	private final static String CLAZZ_LIST = "java.util.List";
	
	private final static String CLAZZ_ARRAYLIST = "java.util.ArrayList";
	
	private final static String CLAZZ_ARRAYS_LIST = "java.util.Arrays$ArrayList";
	
	private final static String CLAZZ_MAP = "java.util.Map";
	
	private final static String CLAZZ_HASHMAP = "java.util.HashMap";
	
//	private final static String CLAZZ_STRING = "java.lang.String";

	/**
	 * Copy the property values of the given source bean into the target bean.
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * <p>This is just a convenience method. For more complex transfer needs,
	 * consider using a full BeanWrapper.
	 * @param source the source bean
	 * @param target the target bean
	 * @throws BeansException if the copying failed
	 * @see BeanWrapper
	 */
	public static void copyProperties(Object source, Object target) throws BeansException {
		copyProperties(source, target, null, (String[]) null);
	}

	/**
	 * Copy the property values of the given source bean into the given target bean.
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * @param source the source bean
	 * @param target the target bean
	 * @param editable the class (or interface) to restrict property setting to
	 * @param ignoreProperties array of property names to ignore
	 * @throws BeansException if the copying failed
	 * @see BeanWrapper
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void copyProperties(Object source, Object target, Class<?> editable, String... ignoreProperties)
			throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new CommonBeansException("Target class [" + target.getClass().getName() +
						"] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

		for (PropertyDescriptor targetPd : targetPds) {
			Method targetWriteMethod = targetPd.getWriteMethod();
			if (targetWriteMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method sourceReadMethod = sourcePd.getReadMethod();
					if (sourceReadMethod != null/* &&
							ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())*/) {
						try {
							if (!Modifier.isPublic(sourceReadMethod.getDeclaringClass().getModifiers())) {
								sourceReadMethod.setAccessible(true);
							}
							Object sourceVal = sourceReadMethod.invoke(source);
							if (sourceVal != null) {
								if (!Modifier.isPublic(targetWriteMethod.getDeclaringClass().getModifiers())) {
									targetWriteMethod.setAccessible(true);
								}
								Class<?> targetClazz = targetWriteMethod.getParameterTypes()[0];
								Object targetVal = null;
								
								Type sourceType = sourceReadMethod.getGenericReturnType();
								if(sourceType instanceof ParameterizedType){// value是集合类型
									Type targetType = targetPd.getReadMethod().getGenericReturnType();
									targetVal = getTargetValOfParameterizedType((ParameterizedType)targetType, (ParameterizedType)sourceType, sourceVal);
								}else if (ClassUtils.isAssignable(targetWriteMethod.getParameterTypes()[0], sourceReadMethod.getReturnType())) { // 同类型
									targetVal = sourceVal;
								} else { // 不同类型
									try {
										targetVal = targetClazz.newInstance();
									} catch (Exception e) {
										targetVal = sourceVal;
									}
									copyProperties(sourceVal, targetVal);
								}
								targetWriteMethod.invoke(target, targetVal);
							}
						}
						catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取集合类型copy后对象
	 * @param innerTargetType
	 * @param innerSourceType
	 * @param sourceObj
	 * @return
	 * @throws Exception
	 */
	private static Object getTargetValOfParameterizedType(ParameterizedType innerTargetType, ParameterizedType innerSourceType, Object sourceObj) throws Exception {
		Object targetVal = null;
		if(CLAZZ_LIST.equals(sourceObj.getClass().getName()) || CLAZZ_ARRAYLIST.equals(sourceObj.getClass().getName()) || CLAZZ_ARRAYS_LIST.equals(sourceObj.getClass().getName())){// List处理
			targetVal = getTargetValOfListType(innerTargetType, innerSourceType, (List) sourceObj);
		}else if(CLAZZ_MAP.equals(sourceObj.getClass().getName()) || CLAZZ_HASHMAP.equals(sourceObj.getClass().getName())){// Map处理
			targetVal = getTargetValOfMapType(innerTargetType, innerSourceType, (Map) sourceObj);
		}else{
			targetVal = sourceObj;
		}
		return targetVal;
	}

	/**
	 * 获取List类型Copy后的对象
	 * @param targetType
	 * @param sourceType
	 * @param sourceList
	 * @return
	 * @throws Exception
	 */
	private static Object getTargetValOfListType(ParameterizedType targetType, ParameterizedType sourceType, List sourceList) throws Exception {
		Object targetVal = null;
		if(sourceList != null && sourceList.size() > 0 && targetType.getActualTypeArguments().length > 0){
			List targetValList = new ArrayList();
			if(targetType.getActualTypeArguments().length == 1 && targetType.getActualTypeArguments()[0] instanceof ParameterizedType){// value还是集合类型
				ParameterizedType innerTargetType = (ParameterizedType) targetType.getActualTypeArguments()[0];
				ParameterizedType innerSourceType = (ParameterizedType) sourceType.getActualTypeArguments()[0];
				for(int i = 0; i < sourceList.size(); i++){
					Object sourceObj = sourceList.get(i);
					targetValList.add(getTargetValOfParameterizedType(innerTargetType, innerSourceType, sourceObj));
				}
				
			}else if(targetType.getActualTypeArguments().length == 2 && targetType.getActualTypeArguments()[1] instanceof ParameterizedType){
				ParameterizedType innerTargetType = (ParameterizedType) targetType.getActualTypeArguments()[1];
				ParameterizedType innerSourceType = (ParameterizedType) sourceType.getActualTypeArguments()[1];
				for(int i = 0; i < sourceList.size(); i++){
					Object sourceObj = sourceList.get(i);
					targetValList.add(getTargetValOfParameterizedType(innerTargetType, innerSourceType, sourceObj));
				}
			}else{
				Class<?> targetGenericType = (Class<?>) targetType.getActualTypeArguments()[0];
				Class<?> sourceClazz = (Class<?>) sourceType.getActualTypeArguments()[0];
				if(ClassUtils.isAssignable(targetGenericType, sourceClazz)){
					targetValList = sourceList;
				}else{
					for(int i = 0; i < sourceList.size(); i++){
						Object sourceObj = sourceList.get(i);
						Object targetObj = targetGenericType.newInstance();
						copyProperties(sourceObj, targetObj);
						targetValList.add(targetObj);
					}
				}
			}
			targetVal = targetValList;
		}else if(sourceList != null && sourceList.size() == 0){
			targetVal = new ArrayList();
		}
		
		return targetVal;
	}

	/**
	 * 获取Map类型copy后的value
	 * @param targetType
	 * @param sourceType
	 * @param sourceMap
	 * @return
	 * @throws Exception
	 */
	private static Object getTargetValOfMapType(ParameterizedType targetType, ParameterizedType sourceType, Map sourceMap) throws Exception {
		Object targetVal = null;
		if(sourceMap != null && sourceMap.size() > 0 && targetType.getActualTypeArguments().length > 1){//Map--value在第二个位置
			Map targetValMap = new HashMap();
			if(targetType.getActualTypeArguments().length == 1 && targetType.getActualTypeArguments()[0] instanceof ParameterizedType){// value还是集合类型
				ParameterizedType innerTargetType = (ParameterizedType) targetType.getActualTypeArguments()[0];
				ParameterizedType innerSourceType = (ParameterizedType) sourceType.getActualTypeArguments()[0];
				Iterator it = sourceMap.keySet().iterator();
				while(it.hasNext()){
					Object key = it.next();
					Object sourceObj = sourceMap.get(key);
					targetValMap.put(key, getTargetValOfParameterizedType(innerTargetType, innerSourceType, sourceObj));
				}
			}else if(targetType.getActualTypeArguments().length == 2 && targetType.getActualTypeArguments()[1] instanceof ParameterizedType){// value还是集合类型
				ParameterizedType innerTargetType = (ParameterizedType) targetType.getActualTypeArguments()[1];
				ParameterizedType innerSourceType = (ParameterizedType) sourceType.getActualTypeArguments()[1];
				Iterator it = sourceMap.keySet().iterator();
				while(it.hasNext()){
					Object key = it.next();
					Object sourceObj = sourceMap.get(key);
					targetValMap.put(key, getTargetValOfParameterizedType(innerTargetType, innerSourceType, sourceObj));
				}
			}else{
				Class<?> sourceClazz = (Class<?>) sourceType.getActualTypeArguments()[1];
				Class<?> targetGenericType = (Class<?>) targetType.getActualTypeArguments()[1];
				if(ClassUtils.isAssignable(targetGenericType, sourceClazz)){
					targetValMap = sourceMap;
				}else{
					Iterator it = sourceMap.keySet().iterator();
					while(it.hasNext()){
						Object key = it.next();
						Object sourceObj = sourceMap.get(key);
						Object targetObj = targetGenericType.newInstance();
						copyProperties(sourceObj, targetObj);
						targetValMap.put(key, targetObj);
					}
				}
				
			}
			targetVal = targetValMap;
		}else if(sourceMap != null && sourceMap.size() == 0){
			targetVal = new HashMap();
		}
		
		return targetVal;
	}
	
//	public static boolean isWrapClass(Class<?> clazz) {
//		try {
//			return ((Class<?>) clazz.getField("TYPE").get(null)).isPrimitive();
//		} catch (Exception e) {
//			return false;
//		}
//	}
//	
//	public static boolean canNewInstance(Class<?> clazz){
//		if (isWrapClass(clazz) || clazz.isInterface() || CLAZZ_STRING.equals(clazz.getName()) 
//				|| CLAZZ_HASHMAP.equals(clazz.getName()) || CLAZZ_ARRAYLIST.equals(clazz.getName())) {
//			return false;
//		}
//		return true;
//	}
	/**
	 * 复制集合
	 * @param sourceList 原集合
	 * @param targetList 目标集合 不能为null
	 * @param targetClass 目标类型
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <S, T> void copyListProperties(List<S> sourceList, List<T> targetList, Class<T> targetClass) throws InstantiationException, IllegalAccessException {
		if (sourceList != null && sourceList.size() > 0) {
			T t = null;
			for (S s : sourceList) {
				t = targetClass.newInstance();
				copyProperties(s, t);
				targetList.add(t);
			}
		}
	}
}
