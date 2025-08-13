package com.hutu.socket.invacationrpc.up;

import com.hutu.socket.invacationrpc.IRUserService;
import com.hutu.socket.invacationrpc.RStudent;
import com.hutu.socket.invacationrpc.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * RPC服务端
 */
public class UpRpcService {
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
                // 依次读取 类名、方法名、参数类型、参数值
                String className = ois.readUTF();
                String methodName = ois.readUTF();
                Class[] parameterTypes = (Class[])ois.readObject();
                Object[] arguments = (Object[])ois.readObject();
                Class<?> implClass = getImplClass(className);
                Object serviceImpl = implClass.getDeclaredConstructor().newInstance();
                // 通过反射调用方法
                Method method = implClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceImpl, arguments);
                // 写回结果
                oos.writeObject(result);
                oos.flush();
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                     IllegalAccessException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Class<?> getImplClass(String interfaceName) throws ClassNotFoundException {
        if (interfaceName.equals(IRUserService.class.getName())) {
            return UserServiceImpl.class;
        }
        throw new ClassNotFoundException("找不到接口实现: " + interfaceName);
    }
}
