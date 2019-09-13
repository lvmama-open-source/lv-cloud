package com.lv.cloud.eventlog;

import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事件日志
 * Created by xiaoyulin on 2017/8/15.
 */
public class EventLog {
	
    private Logger log;

    public EventLog(){}

    public EventLog(Class clazz){
        log = LoggerFactory.getLogger(clazz);
    }
        
    /**
     * 输出系统事件日志
     * @param bussinessCode 业务/模块编码
     * @param errorCode 错误码
     * @param message 日志描述（要求简短）
     * @param detail 日志详情
     * @param ex 异常
     * @param bussinessId 业务id（比如订单号）
     * @param tag 标记（便于检索）
     */
    public void outputSystemEventLog(String bussinessCode, String errorCode, String message, String detail, Throwable ex, Object bussinessId, String tag){ 
    	try {
			// 调用方法
    		int i = 2;
			outputSystemEventLog(bussinessCode, errorCode, message, detail, ex, bussinessId, tag, Thread.currentThread().getStackTrace()[i].toString());
		} catch (Exception e) {
			log.error("输出系统事件日志失败，{}", e); 
		}
    }
    
    /**
     * 输出系统事件日志<br/>
     * 格式：“LVMM_EVENT_LOG:{}|BUSSINESS_CODE:{}|ERROR_CODE:{}BUSSSINESS_ID:{}|TAG:{}|MESSAGE:{}|DETAIL:{}||STACK_TRACE:{}|EXCEPTION:{}”
     * @param bussinessCode 业务/模块编码
     * @param errorCode 错误码
     * @param message 日志描述（要求简短）
     * @param detail 日志详情
     * @param ex 异常
     * @param bussinessId 业务id（比如订单号）
     * @param tag 标记（便于检索）
     * @param stackTrace 日志记录位置
     */
    public void outputSystemEventLog(String bussinessCode, String errorCode, String message, String detail, 
    		Throwable ex, Object bussinessId, String tag, String stackTrace) {
    	try {
			String nullStr = "null";
			String logContent = Joiner.on("|").join(
					Joiner.on("=").useForNull(nullStr).join("LVMM_EVENT_LOG", LoggingEnum.LOG_EVENT.SYSTEM.getCode()),
					Joiner.on("=").useForNull(nullStr).join("BUSSINESS_CODE", bussinessCode),
					Joiner.on("=").useForNull(nullStr).join("ERROR_CODE", errorCode),
					Joiner.on("=").useForNull(nullStr).join("BUSSINESS_ID", bussinessId),
					Joiner.on("=").useForNull(nullStr).join("TAG", tag),
					Joiner.on("=").useForNull(nullStr).join("MESSAGE", message),
					Joiner.on("=").useForNull(nullStr).join("DETAIL", detail),					
					Joiner.on("=").useForNull(nullStr).join("STACK_TRACE", stackTrace),
					Joiner.on("=").useForNull(nullStr).join("EXCEPTION", "{}")
					).toString();
			log.error(logContent, ex);
		} catch (Exception e) {
			log.error("输出系统事件日志失败，{}", e); 
		}
    }
    
    /**
     * 记录简洁业务事件日志（info级别）,带业务模块编码
     * @param bussinessCode 业务/模块编码
     * @param bussinessId 业务id（比如订单号）
     * @param tag 标记（便于检索）
     * @param message 日志描述（要求简短）
     * 
     */
    public void infoLogicEventLog(String bussinessCode, Object bussinessId, String tag, String message){
        try {
			// 输出日志
			int i = 2;
			log.info(getLogicEventLog(bussinessCode, null, bussinessId, tag, message, null, Thread.currentThread().getStackTrace()[i].toString()));
		} catch (Exception e) {
			log.error("记录业务事件日志失败，{}", e); 
		}
    }
    
    /**
     * 记录详细业务事件日志（info级别）,带业务模块编码
     * @param bussinessCode 业务/模块编码
     * @param bussinessId 业务id（比如订单号）
     * @param tag 标记（便于检索）
     * @param message 日志描述（要求简短）
     * @param detail 日志详情
     */
    public void infoLogicEventLog(String bussinessCode, Object bussinessId, String tag, String message, String detail){
        try {
			// 输出日志
			int i = 2;
			log.info(getLogicEventLog(bussinessCode, null, bussinessId, tag, message, detail, Thread.currentThread().getStackTrace()[i].toString()));
		} catch (Exception e) {
			log.error("记录业务事件日志失败，{}", e); 
		}
    }
    
    /**
     * 记录详细业务事件日志（warn级别）,带业务模块编码
     * @param message 日志描述（要求简短）
     * @param bussinessId 业务id（比如订单号）
     * @param tag 标记（便于检索）
     * @param bussinessCode 业务/模块编码
     */
    public void warnLogicEventLog(String bussinessCode, Object bussinessId, String tag, String message, String detail){
        try {
			// 输出日志
			int i = 2;
			log.warn(getLogicEventLog(bussinessCode, null, bussinessId, tag, message, detail, Thread.currentThread().getStackTrace()[i].toString()));
		} catch (Exception e) {
			log.error("记录业务事件日志失败，{}", e); 
		}
    }
    
    /**
     * 记录简洁业务事件日志（error级别）,带业务模块编码和错误码
     * @param bussinessCode 业务/模块编码
     * @param errorCode 错误码
     * @param bussinessId 业务id（比如订单号）
     * @param tag 标记（便于检索）
     * @param message 日志描述（要求简短）
     * 
     */
    public void errorLogicEventLog(String bussinessCode, String errorCode, Object bussinessId, String tag, String message){
        try {
			// 输出日志
			int i = 2;
			log.error(getLogicEventLog(bussinessCode, errorCode, bussinessId, tag, message, null, Thread.currentThread().getStackTrace()[i].toString()));
		} catch (Exception e) {
			log.error("记录业务事件日志失败，{}", e); 
		}
    }
    
    /**
     * 记录详细业务事件日志（error级别）,带业务模块编码和错误码
     * @param bussinessCode 业务/模块编码
     * @param errorCode 错误码
     * @param bussinessId 业务id（比如订单号）
     * @param tag 标记（便于检索）
     * @param message 日志描述（要求简短）
     * @param detail 日志详情
     */
    public void errorLogicEventLog(String bussinessCode, String errorCode, Object bussinessId, String tag, String message, String detail){
        try {
			// 输出日志
			int i = 2;
			log.error(getLogicEventLog(bussinessCode, errorCode, bussinessId, tag, message, detail, Thread.currentThread().getStackTrace()[i].toString()));
		} catch (Exception e) {
			log.error("记录业务事件日志失败，{}", e); 
		}
    }
    
    /**
     * 记录详细业务事件日志（error级别）,带业务模块编码
     * @param bussinessCode 业务/模块编码
     * @param bussinessId 业务id（比如订单号）
     * @param tag 标记（便于检索）
     * @param message 日志描述（要求简短）
     * @param detail 日志详情
     */
    public void errorLogicEventLog(String bussinessCode, Object bussinessId, String tag, String message, String detail){
        try {
			// 输出日志
			int i = 2;
			log.error(getLogicEventLog(bussinessCode, null, bussinessId, tag, message, detail, Thread.currentThread().getStackTrace()[i].toString()));
		} catch (Exception e) {
			log.error("记录业务事件日志失败，{}", e); 
		}
    }
    
    /**
     * 生成业务事件日志（新版）<br/>
     * 格式：“LVMM_EVENT_LOG:{}|BUSSINESS_CODE:{}|ERROR_CODE:{}|BUSSSINESS_ID:{}|TAG:{}|MESSAGE:{}|DETAIL:{}|STACK_TRACE:{}”
     * @param bussinessCode 业务/模块编码
     * @param errorCode 错误码
     * @param message 日志内容（要求简短）
     * @param detail 日志详情
     * @param bussinessId 业务id（比如订单号）
     * @param tag 标记（便于检索）
     * @param bussinessCode 业务模块编码
     * @param stackTrace 
     * @return
     */
    private String getLogicEventLog(String bussinessCode, String errorCode, Object bussinessId, String tag, String message, String detail, String stackTrace){
    	String nullStr = "null";
        String logContent = Joiner.on("|").join(
        		Joiner.on("=").useForNull(nullStr).join("LVMM_EVENT_LOG", LoggingEnum.LOG_EVENT.LOGIC.getCode()),
        		Joiner.on("=").useForNull(nullStr).join("BUSSINESS_CODE", bussinessCode),
        		Joiner.on("=").useForNull(nullStr).join("ERROR_CODE", errorCode),
                Joiner.on("=").useForNull(nullStr).join("BUSSINESS_ID", bussinessId),
                Joiner.on("=").useForNull(nullStr).join("TAG", tag),
                Joiner.on("=").useForNull(nullStr).join("MESSAGE", message),
                Joiner.on("=").useForNull(nullStr).join("DETAIL", detail),
		        Joiner.on("=").useForNull(nullStr).join("STACK_TRACE", stackTrace)
        		).toString();
        return logContent;
    }

    public void info(Object message){
        log.info(Thread.currentThread().getStackTrace()[2].toString() + message);
    }

    public void debug(Object message){
        log.debug(Thread.currentThread().getStackTrace()[2].toString() + message);
    }

    public void error(Object message){
        log.error(Thread.currentThread().getStackTrace()[2].toString() + message);
    }

    public void error(Object message, Throwable t){
        log.error(Thread.currentThread().getStackTrace()[2].toString() + message, t);
    }

    public void warn(Object message){
        log.warn(Thread.currentThread().getStackTrace()[2].toString() + message);
    }
    
    public boolean isDebugEnabled(){
    	return log.isDebugEnabled();
    }
    
    public Logger getLogger(){
    	return log;
    }
}
