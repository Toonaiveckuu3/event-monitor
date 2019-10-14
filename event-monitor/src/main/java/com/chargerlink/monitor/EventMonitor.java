package com.chargerlink.monitor;


import com.chargerlink.monitor.event.MonitorEvent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * 观察者管理
 *
 * @Author: ZhangHeng zhanghenggoog@gmail.com
 * @Date: Created on 11:49 2018/11/20.
 */
@Slf4j
public class EventMonitor<T extends MonitorEvent> implements IListenerManager, INoticeManager<T> {

    /**
     * 线程池初始化大小，默认15
     */
    private Integer corePoolSize = 15;

    /**
     * 线程池最大线程数,默认30
     */
    private Integer maximumPoolSize = 30;

    /**
     * 线程池线程等待存活时间,单位毫秒:{@code TimeUnit.MILLISECONDS},默认200
     */
    private Long keepAliveTime = 200L;

    /**
     * 异步监听的线程池
     */
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 监听MAP
     */
    private ConcurrentHashMap<String, CopyOnWriteArrayList<MonitorEventListener>> listenerMap;

    public EventMonitor() {
        this.listenerMap = new ConcurrentHashMap<>();
        this.threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(corePoolSize));
    }

    /**
     * 新增同步监听-在新增监听之后，会阻塞线程，直到callback()回调结果返回或监听超时
     *
     * @param listener
     */
    @Override
    public void addWaitListener(MonitorEventListener listener, Long timeOut) {
        putListener(listener);
        synchronized (listener.countDownLatch) {
            try {
                listener.countDownLatch.await(timeOut, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                listener.failureCallBack(listener.getMonitorEvent(), e);
                e.printStackTrace();
            }
        }
        if (listener.countDownLatch.getCount() > 0) {
            listener.timeOutCallBack(listener.getMonitorEvent());
        }
    }

    /**
     * 新增监听-新增监听后在新的线程中开启监听
     *
     * @param listener
     */
    @Override
    public void addListener(final MonitorEventListener listener, final Long timeOut) {
        putListener(listener);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (listener.countDownLatch) {
                    try {
                        listener.countDownLatch.await(timeOut, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        listener.failureCallBack(listener.getMonitorEvent(), e);
                        e.printStackTrace();
                    }
                }
                if (listener.countDownLatch.getCount() > 0) {
                    listener.timeOutCallBack(listener.getMonitorEvent());
                }
            }
        };
        threadPoolExecutor.execute(runnable);
    }

    /**
     * 新增监听
     *
     * @param listener
     */
    @Override
    public void addListener(MonitorEventListener listener) {
        putListener(listener);
    }

    /**
     * 移除监听
     *
     * @param listener
     */
    @Override
    public void removeListener(MonitorEventListener listener) {
        CopyOnWriteArrayList<MonitorEventListener> listeners = listenerMap.get(listener.getEventFlag());
        if (listeners == null) {
            return;
        }
        listeners.remove(listener);
        log.info("移除监听EventFlag:[{}],监听总数[{}]", listener.getEventFlag(), listeners.size());
        if (listeners.isEmpty()) {
            listenerMap.remove(listener.getEventFlag());
        }

    }

    /**
     * 事件发送
     *
     * @param monitorEvent
     */
    @Override
    public void send(T monitorEvent) {
        log.debug("send event with event flag:[eventFlag = {}]", monitorEvent.getEventFlag());
        if (!listenerMap.containsKey(monitorEvent.getEventFlag())) {
            log.debug("未找到相应监听[eventFlag = {}]", monitorEvent.getEventFlag());
            return;
        }
        dispatcher(monitorEvent);
    }

    /**
     * 事件分发器
     *
     * @param monitorEvent
     */
    private void dispatcher(MonitorEvent monitorEvent) {
        CopyOnWriteArrayList<MonitorEventListener> listeners = listenerMap.get(monitorEvent.getEventFlag());
        if (listeners != null) {
            for (MonitorEventListener eventListener : listeners) {
                eventListener.callBack(monitorEvent);
                if (eventListener.countDownLatch.getCount() > 0) {
                    eventListener.countDownLatch.countDown();
                }
            }
            log.info("事件EventFlag=[{}]回调完成，共[{}]次", monitorEvent.getEventFlag(), listeners.size());
        }
    }

    /**
     * 新增监听器
     *
     * @param listener
     */
    private void putListener(MonitorEventListener listener) {
        CopyOnWriteArrayList<MonitorEventListener> listeners = listenerMap.get(listener.getEventFlag());
        if (listeners == null) {
            listeners = new CopyOnWriteArrayList<MonitorEventListener>();
        }
        listeners.add(listener);
        listenerMap.put(listener.getEventFlag(), listeners);
        log.info("新增监听EventFlag:[{}],监听总数[{}]", listener.getEventFlag(), listeners.size());
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }
}
