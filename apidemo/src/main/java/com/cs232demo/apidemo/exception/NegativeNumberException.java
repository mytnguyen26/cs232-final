package com.cs232demo.apidemo.exception;

public class NegativeNumberException extends Exception {
    public NegativeNumberException() {
        super("Exception: Cannot have negative number");
    }
}
