package com.hutu.socket.invacationrpc;

import com.hutu.socket.invacationrpc.up.UpRpcClient;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        IRUserService service = UpRpcClient.instance(IRUserService.class);
        System.out.println(service.getStudent1(1L));
    }
}
