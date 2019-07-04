package com.lv.cloud.eventlog;

/**
 * api接口公用枚举
 * Created by xiaoyulin on 2017/8/10.
 */
public class LoggingEnum {

    /**
     * 日志事件
     */
    public static enum LOG_EVENT {

        LOGIC("业务事件日志"),
        SYSTEM("系统事件日志");

        private String code;
        private String cnName;

        LOG_EVENT(String cnName) {
            this.cnName = cnName;
        }

        public String getCode(){
            return this.name();
        }

        public String getCnName() {
            return cnName;
        }
    }
}
