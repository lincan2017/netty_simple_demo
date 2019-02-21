package io;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @author : Lin Can
 * @date : 2019/2/21 10:37
 */
public class IOClient {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8000);
                while (true) {
                    socket.getOutputStream().write((new Date() + " hello ioServer!").getBytes());
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
