package eventmonitort.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: ZhangHeng zhanghenggoog@gmail.com
 * @Date: Created on 13:14 2019/1/10.
 */
public class Client {
    private static PrintWriter pw = null;
    private static BufferedReader br = null;
    private static Socket s;
    static Scanner scanner = new Scanner(System.in);
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            Socket s = new Socket(InetAddress.getLocalHost(), 8099);
            pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            while (true) {
                System.out.println("Client端请输入：");
                String str = scanner.next();
                pw.println(str);
                pw.flush();
                if (str.equals("exit")) {
                    break;
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            br.close();
            pw.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
