package com.lv.cloud.message;

public interface Subscriber {
    /**
     * 消息处理方法
     * @param message
     */
    public void onMessageEvent(LvMessage message);
}
