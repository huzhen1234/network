package com.hutu.socket.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 模拟RPC客户端远程调用
 */
public class RpcClient {
    public static Student getStudent(Long id) throws IOException {
        // 建立连接 套接字 基于 IP/UTP
        try (Socket socket = new Socket("127.0.0.1", 8888);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            Student student = new Student();
            // 写入数据
            outputStream.writeUTF(String.valueOf(id));
            outputStream.flush();
            String name = inputStream.readUTF();
            student.setId(id);
            student.setName(name);
            return student;
        }
    }
}
