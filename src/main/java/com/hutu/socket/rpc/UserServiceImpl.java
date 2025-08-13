package com.hutu.socket.rpc;

public class UserServiceImpl implements IUserService{
    @Override
    public Student getStudent1(Long id) {
        return new Student("hutu",400 + id);
    }

    @Override
    public Student getStudent2(Long id) {
        return new Student("heifenli",500 + id);
    }
}
