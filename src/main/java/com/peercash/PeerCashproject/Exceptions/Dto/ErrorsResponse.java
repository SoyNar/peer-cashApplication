package com.peercash.PeerCashproject.Exceptions.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
public class ErrorsResponse {

    private LocalDateTime timestamp;
    private int code;
    private String message;
    private Map<String, String> errors;
}
