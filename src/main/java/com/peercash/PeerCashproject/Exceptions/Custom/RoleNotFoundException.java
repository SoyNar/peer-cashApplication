package com.peercash.PeerCashproject.Exceptions.Custom;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
