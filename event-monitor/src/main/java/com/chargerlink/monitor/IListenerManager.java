package com.chargerlink.monitor;

/**
 * 监听管理接口类
 * @Author: ZhangHeng zhanghenggoog@gmail.com
 * @Date: Created on 11:18 2018/11/20.
 */
public interface IListenerManager {

    /**
     * 新增同步监听-在新增监听之后，会阻塞线程，直到callback()回调结果返回或监听超时
     * @param monitorEventListener
     */
    void addWaitListener(MonitorEventListener monitorEventListener, Long timeOut);

    /**
     * 新增监听-当超过设置的超时时间后将出发超时回调
     * @param monitorEventListener
     */
    void addListener(MonitorEventListener monitorEventListener, Long timeOut);

    /**
     * 新增监听-不设置超时时间，不会出发超时回调
     * @param monitorEventListener
     */
    void addListener(MonitorEventListener monitorEventListener);

    /**
     * 移除监听
     * @param monitorEventListener
     */
    void removeListener(MonitorEventListener monitorEventListener);
}
