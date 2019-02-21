package io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : Lin Can
 * @date : 2019/2/21 10:23
 */
public class IOServer {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8000);

        //(1)接收新连接线程
        new Thread(() -> {
            while (true) {
                try {
                    //(1)阻塞方法获取新连接
                    Socket socket = serverSocket.accept();
                    //(2)每当有新连接都要分配新线程读取数据
                    new Thread(() -> {
                        int len;
                        byte[] dataBuf = new byte[1024];
                        try {
                            InputStream inputStream = socket.getInputStream();
                            //字节流的方式读取数据
                            while ((len = inputStream.read(dataBuf)) != -1) {
                                System.out.println(new String(dataBuf,0,len));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
