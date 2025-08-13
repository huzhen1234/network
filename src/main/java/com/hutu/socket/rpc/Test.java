package com.hutu.socket.rpc;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Student student = RpcClient.getStudent(100L);
        System.out.println(student.toString());
    }
}
