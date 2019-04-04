package com.chargerlink.monitor.event;

/**
 * 异步事件
 * @Author: ZhangHeng zhanghenggoog@gmail.com
 * @Date: Created on 14:06 2019/1/10.
 */
public class AsyncMonitorEvent extends MonitorEvent{
    /**
     * 初始化事件-必须初始化事件的唯一标识
     *
     * @param eventFlag
     */
    public AsyncMonitorEvent(String eventFlag) {
        super(eventFlag);
    }
}
