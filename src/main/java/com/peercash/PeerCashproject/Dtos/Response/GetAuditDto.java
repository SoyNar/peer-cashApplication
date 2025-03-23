package com.peercash.PeerCashproject.Dtos.Response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAuditDto {
    private Long id;
    private String entity;
    private String action;
    private String createAt;
    private String updateAt;
    private String ipAddress;
    private String userAgent;
    private String status;
    private String details;
    private String description;
    private long duration;
}
