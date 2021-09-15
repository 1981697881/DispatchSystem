package com.dispatch.system.http;

public class MyException extends Exception {
    private int code;

    public MyException(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
