package eventmonitort.server;

import com.chargerlink.monitor.EventMonitor;
import com.chargerlink.monitor.EventMonitorFactory;
import com.chargerlink.monitor.MonitorEventListener;
import com.chargerlink.monitor.event.MonitorEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ZhangHeng zhanghenggoog@gmail.com
 * @Date: Created on 12:17 2019/1/10.
 */
@Slf4j
public class Server {
    private static EventMonitor eventMonitor = EventMonitorFactory.getInstance();

    private static BufferedReader bufferedReader = null;
    private static PrintWriter printWriter = null;
    private static ServerSocket serverSocket;
    private static Socket socket;

    /**
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            MonitorEvent monitorEvent = new MonitorEvent("event" + i % 2);
            log.info("建立监听:{}", monitorEvent.getEventFlag());
            eventMonitor.addListener(new MonitorEventListener<MonitorEvent>(monitorEvent) {
                @Override
                protected void callBack(MonitorEvent monitorEvent) {
                    if (monitorEvent instanceof StatusMonitorEvent) {
                        log.info("强转成功");
                    }
                    log.info("callBack监听到了" + monitorEvent.getEventFlag());
                }

                @Override
                protected void timeOutCallBack(MonitorEvent monitorEvent) {
                    log.info(monitorEvent.getEventFlag());
                }

                @Override
                protected void failureCallBack(MonitorEvent monitorEvent, Exception e) {
                    log.info(monitorEvent.getEventFlag());
                }
            }, 300000L);
        }
        StatusMonitorEvent mainMonitorEvent = new StatusMonitorEvent("event" + 15);
        eventMonitor.send(mainMonitorEvent);
        try {
            serverSocket = new ServerSocket(8099);
            System.out.println("服务器正常启动。。。。");
            socket = serverSocket.accept();//阻塞方法
            System.out.println("连接成功" + socket.getRemoteSocketAddress());
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                String string = bufferedReader.readLine();
                System.out.println("Server读到：" + string);
                StatusMonitorEvent monitorEvent = new StatusMonitorEvent("event" + string);
                monitorEvent.setSuccess(string.length() > 1);
                eventMonitor.send(monitorEvent);
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            printWriter.close();
            bufferedReader.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

}
