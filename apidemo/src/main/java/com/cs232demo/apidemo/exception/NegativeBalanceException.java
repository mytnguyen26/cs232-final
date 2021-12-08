package com.cs232demo.apidemo.exception;

public class NegativeBalanceException extends Exception{
    public NegativeBalanceException() {
        super("Exception: Cannot have negative balance");
    }
}
