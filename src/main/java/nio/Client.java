package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author : Lin Can
 * @date : 2019/2/21 15:51
 */
public class Client {
    public static void main(String[] args) {
        new Thread(() -> {
            /*try {
                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.bind(new InetSocketAddress("localhost", 8000))
                Charset.defaultCharset().newEncoder().encode(new Date() + " hello ioServer!")
                while (true) {
                    socket.getOutputStream().write(().getBytes());
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }).start();
    }
}
