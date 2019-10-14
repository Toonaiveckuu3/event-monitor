package eventmonitort.server;

import com.chargerlink.monitor.event.MonitorEvent;

/**
 * @Author: ZhangHeng zhanghenggoog@gmail.com
 * @Date: Created on 0:21 2019/1/31.
 */
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

    public StatusMonitorEvent(String eventFlag, boolean success) {
        super(eventFlag);
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
