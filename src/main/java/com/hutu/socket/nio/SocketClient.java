package com.hutu.socket.nio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 客户端
 * bio模式下
 * 同步阻塞
 *
 * 客户端 先写数据，再读数据
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        // 建立连接
        Socket socket = null;
        // 输入输出流
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        // 通信的地址
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8888);
        try {
            socket = new Socket();
            // 连接服务器
            socket.connect(address);

            // =============和我们的socket进行绑定 输入输出顺序别写反，容易导致死锁==============

            // 写网络数据
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            // 读网络数据
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeUTF("hello world"); // 进到缓存区
            outputStream.flush(); // 刷出缓存区 进入服务区
            // 接收服务器数据
            System.out.println(inputStream.readUTF());  // 接收到响应
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (socket != null) {
                socket.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
