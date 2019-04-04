package com.chargerlink.monitor;


import com.chargerlink.monitor.event.MonitorEvent;

/**
 * 通知管理接口
 * @Author: ZhangHeng zhanghenggoog@gmail.com
 * @Date: Created on 11:39 2018/11/20.
 */
public interface INoticeManager<T extends MonitorEvent> {

    /**
     * 接收变更通知
     * @param monitorEvent
     */
    void send(T monitorEvent);
}
