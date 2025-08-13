package com.hutu.socket.invacationrpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * RPC服务端
 */
public class RpcService {
    public static void main(String[] args) throws IOException {
        // 监听 8888 端口
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务端已启动~~~~");
        while (true) {  // 持续监听客户端连接
            try (
                    Socket socket = serverSocket.accept();
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())
            ) {
                String id = ois.readUTF();
                IRUserService userService = new UserServiceImpl();
                RStudent student1 = userService.getStudent1(Long.valueOf(id));
                oos.writeUTF(student1.getName());
                oos.flush();
            }
        }
    }
}
