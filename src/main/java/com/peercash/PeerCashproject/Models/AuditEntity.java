package com.peercash.PeerCashproject.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "audit")
public class AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameEntity;
    private String action;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String ipAddress;
    private String userAgent;
    private String status;
    private String details;
    private String description;
    private long duration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
