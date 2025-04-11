package com.peercash.PeerCashproject.Exceptions.Custom;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
