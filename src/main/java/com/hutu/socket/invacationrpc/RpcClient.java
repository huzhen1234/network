package com.hutu.socket.invacationrpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * 模拟RPC客户端远程调用
 * 使用动态代理，此时调用该接口的方法时就不会走具体的实现类了，而是会走handler里面的具体逻辑
 */
public class RpcClient {
    public static IRUserService instance() throws IOException {
        // 创建代理对象
        InvocationHandler handler = ((proxy, method, args) -> {
            // 建立连接 套接字 基于 IP/UTP
            try (Socket socket = new Socket("127.0.0.1", 8888);
                 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
                RStudent student = new RStudent();
                // 写入数据
                outputStream.writeUTF("354");
                outputStream.flush();
                String name = inputStream.readUTF();
                student.setId(354L);
                student.setName(name);
                return student;
            }
        });
        // 创建实例，需要类加载器
        Object o = Proxy.newProxyInstance(IRUserService.class.getClassLoader(), new Class[]{IRUserService.class}, handler);
        return (IRUserService) o;
    }
}
