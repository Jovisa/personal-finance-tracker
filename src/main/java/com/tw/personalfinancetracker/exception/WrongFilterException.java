package com.tw.personalfinancetracker.exception;

public class WrongFilterException extends RuntimeException{
    public WrongFilterException(String message) {
        super(message);
    }
}
