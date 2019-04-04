package com.chargerlink.monitor.event;

/**
 * 事件
 * @Author: ZhangHeng zhanghenggoog@gmail.com
 * @Date: Created on 23:06 2018/11/20.
 */
public class MonitorEvent {

    /**
     * 事件的唯一标识
     */
    private final String eventFlag;

    /**
     * 事件附属数据
     */
    Object extraObject;

    /**
     * 初始化事件-必须初始化事件的唯一标识
     * @param eventFlag
     */
    public MonitorEvent(String eventFlag) {
        if (eventFlag == null) {
            throw new NullPointerException("event flag must not be null");
        }
        this.eventFlag = eventFlag;
    }

    public String getEventFlag() {
        return eventFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonitorEvent)) return false;

        MonitorEvent that = (MonitorEvent) o;

        return eventFlag.equals(that.eventFlag);
    }

    @Override
    public int hashCode() {
        return eventFlag.hashCode();
    }

    public Object getExtraObject() {
        return extraObject;
    }

    public void setExtraObject(Object extraObject) {
        this.extraObject = extraObject;
    }
}
