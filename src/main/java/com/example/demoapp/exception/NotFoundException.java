package com.example.demoapp.exception;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException() {
        super("リソースが見つかりません");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
