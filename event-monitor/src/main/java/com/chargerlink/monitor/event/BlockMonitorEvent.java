package com.chargerlink.monitor.event;

/**
 * 阻塞事件
 * @Author: ZhangHeng zhanghenggoog@gmail.com
 * @Date: Created on 14:07 2019/1/10.
 */
public class BlockMonitorEvent extends MonitorEvent{
    /**
     * 初始化事件-必须初始化事件的唯一标识
     *
     * @param eventFlag
     */
    public BlockMonitorEvent(String eventFlag) {
        super(eventFlag);
    }
}
