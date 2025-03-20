package com.peercash.PeerCashproject.Exceptions.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private int code;
    private LocalDate date;
    private String message;
}
