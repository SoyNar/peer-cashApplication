package com.peercash.PeerCashproject.Exceptions.Custom;

public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException(String message) {
        super(message);
    }
}
