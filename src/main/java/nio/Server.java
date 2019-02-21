package nio;

import sun.nio.ch.SelectionKeyImpl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 用JDK自带的NIO方式编写服务器
 * @author : Lin Can
 * @date : 2019/2/21 15:02
 */
public class Server {
    public static void main(String[] args) throws IOException {
        Selector serverSelector = Selector.open();
        Selector clientSelector = Selector.open();

        new Thread(() -> {
            try {
                // 对应IO编程中服务端启动
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(8000));
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(serverSelector, SelectionKeyImpl.OP_ACCEPT);

                while (true) {
                    // 监测是否有新的连接，这里的1指的是阻塞的时间为 1ms
                    if (serverSelector.select(1) > 0) {
                        Set<SelectionKey> serverSelectionKey = serverSelector.selectedKeys();
                        Iterator<SelectionKey> serverKeyIterator = serverSelectionKey.iterator();

                        while (serverKeyIterator.hasNext()) {
                            SelectionKey selectionKey = serverKeyIterator.next();
                            if (selectionKey.isAcceptable()) {
                                try {
                                    // (1) 每来一个新连接，不需要创建一个线程，而是直接注册到clientSelector
                                    SocketChannel clientChannel = ((ServerSocketChannel)selectionKey.channel()).accept();
                                    clientChannel.configureBlocking(false);
                                    clientChannel.register(clientSelector,SelectionKeyImpl.OP_READ);
                                } finally {
                                    serverKeyIterator.remove();
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                while (true) {
                    // (2) 批量轮询是否有哪些连接有数据可读，这里的1指的是阻塞的时间为 1ms
                    if (clientSelector.select(1) > 0) {
                        Set<SelectionKey> clientSelectorKeys = clientSelector.selectedKeys();
                        Iterator<SelectionKey> clientSelectorIterator = clientSelectorKeys.iterator();

                        while (clientSelectorIterator.hasNext()) {
                            SelectionKey selectionKey = clientSelectorIterator.next();

                            if (selectionKey.isReadable()) {
                                try {
                                    SocketChannel clientChannel = (SocketChannel)selectionKey.channel();
                                    // (3) 面向 Buffer
                                    ByteBuffer byteBuff = ByteBuffer.allocate(1024);
                                    clientChannel.read(byteBuff);
                                    byteBuff.flip();
                                    System.out.println(Charset.defaultCharset().newDecoder().decode(byteBuff)
                                            .toString());
                                } finally {
                                    clientSelectorIterator.remove();
                                    selectionKey.interestOps(SelectionKeyImpl.OP_READ);
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    /*
     * 与传统IO编写的区别是：
     * 1 不用每一个客户端连接上后分配一个新线程专门监听数据的读取
     * 2 处于阻塞状态的线程大大减少
     * 3 用ByteBuffer包装数据，无需自己缓存字节流
     */
}
