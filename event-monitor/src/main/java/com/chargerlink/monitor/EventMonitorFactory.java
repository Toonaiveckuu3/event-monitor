package com.chargerlink.monitor;

/**
 * @Author: ZhangHeng zhanghenggoog@gmail.com
 * @Date: Created on 11:48 2018/11/20.
 */
public class EventMonitorFactory {

    /**
     * 实例内部类
     */
    private static class EventMonitorInstance{
        /**
         * 事件监听控制器
         */
        private static final EventMonitor INSTANCE = new EventMonitor();
    }

    /**
     * 获取实例
     * @return
     */
    public static EventMonitor getInstance() {
        return EventMonitorInstance.INSTANCE;
    }
}
