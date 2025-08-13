package com.hutu.socket.invacationrpc;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        IRUserService service = RpcClient.instance();
        System.out.println(service.getStudent1(1L));
    }
}
