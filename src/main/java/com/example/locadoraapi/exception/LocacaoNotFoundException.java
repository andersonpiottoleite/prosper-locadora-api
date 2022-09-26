package com.example.locadoraapi.exception;

public class LocacaoNotFoundException extends RuntimeException{

    public LocacaoNotFoundException(String message) {
        super(message);
    }
}
