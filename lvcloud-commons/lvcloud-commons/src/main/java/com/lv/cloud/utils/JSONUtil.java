package com.lv.cloud.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by libing on 2015/5/7.BizCategoryPropList_1
 */
public class JSONUtil {
    protected static final Log LOG = LogFactory.getLog(Object.class);
    /**
     * 将 json 串转换成 JavaBean
     * @param jsonSt
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T json2Bean(String jsonSt, Class<T> clazz){
        try{
            JSONObject jsonObject = JSONObject.fromObject(jsonSt);
            return (T) JSONObject.toBean(jsonObject, clazz);
        }catch(Exception ex){
            LOG.error(ExceptionFormatUtil.getTrace(ex));
            return null;
        }
    }

    /**
     * 支持简单的List<JavaBean> 类型
     * @param jsonSt    合法的 json 串
     * @param clazz     集合中的范型类
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonArray2Bean(String jsonSt, Class<T> clazz){
        try{
            List<T> retCol = new ArrayList<T>();
            JSONArray jsonArray = JSONArray.fromObject(jsonSt);
            for(int i=0;i<jsonArray.size();i++){
                T oneObj = json2Bean(jsonArray.get(i).toString(), clazz);
                if(oneObj!=null)    retCol.add(oneObj);
            }
            return retCol;
        }catch(Exception ex){
            LOG.error(ExceptionFormatUtil.getTrace(ex));
            return null;
        }
    }

    /**
     * 将JavaBean 转换成 json 串
     * @param obj
     * @return
     */
    public static String bean2Json(Object obj){
        try{
            String json ;
            if (obj instanceof java.util.Collection) {
                json = JSONArray.fromObject(obj).toString();
            } else {
                json = JSONObject.fromObject(obj).toString();
            }
            return json;
        }catch(Exception ex){
            LOG.error(ExceptionFormatUtil.getTrace(ex));
            return null;
        }
    }
}
