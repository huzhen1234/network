package com.hutu.socket.invacationrpc;


public class UserServiceImpl implements IRUserService {
    @Override
    public RStudent getStudent1(Long id) {
        return new RStudent("hutu",400 + id);
    }

    @Override
    public RStudent getStudent2(Long id) {
        return new RStudent("heifenli",500 + id);
    }
}
