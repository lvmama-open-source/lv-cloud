package com.lv.cloud.eventlog;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件日志工厂
 * Created by xiaoyulin on 2017/8/15.
 */
public class EventLogFactory {

    private static Map<Class, EventLog> orderLogMap = new HashMap<Class, EventLog>();

    public static EventLog getLog(Class clazz){
        EventLog orderLog = orderLogMap.get(clazz);
        if(orderLog == null){
            orderLog = new EventLog(clazz);
            orderLogMap.put(clazz, orderLog);
        }
        return orderLog;
    }
}
