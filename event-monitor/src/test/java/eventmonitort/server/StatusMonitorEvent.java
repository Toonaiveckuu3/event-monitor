package eventmonitort.server;

import com.chargerlink.monitor.event.MonitorEvent;

import lombok.Data;

/**
 * @Author: ZhangHeng zhanghenggoog@gmail.com
 * @Date: Created on 0:21 2019/1/31.
 */
@Data
public class StatusMonitorEvent extends MonitorEvent {

    boolean success;

    /**
     * 初始化事件-必须初始化事件的唯一标识
     *
     * @param eventFlag
     */
    public StatusMonitorEvent(String eventFlag) {
        super(eventFlag);
    }


}
