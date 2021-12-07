package com.cs232demo.apidemo.exception;

public class NonExistingIDException extends Exception {
    public NonExistingIDException() {
        super("Exception: Item Not Found");
    }
}