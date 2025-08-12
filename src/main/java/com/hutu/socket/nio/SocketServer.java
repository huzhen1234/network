package com.hutu.socket.nio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 * bio模式下
 * 同步阻塞
 *
 * 服务端 先读后写
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        // 跟客户端不同，这里使用的是监听
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务端已启动~~~~");
        while (true) {
            // 阻塞方法，等待客户端连接 接收客户端传输过来的数据
            // accept() → 返回已连接的 Socket
            new Thread(new ServerTask(serverSocket.accept())).start();
        }
    }

    private static class ServerTask implements Runnable {
        private Socket socket;

        public ServerTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream()
                    );
            ) {
                // 读取客户端传递过来的数据
                String receive = inputStream.readUTF();
                // 响应客户端
                outputStream.writeUTF("服务端已收到~~~" + receive);
                outputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
