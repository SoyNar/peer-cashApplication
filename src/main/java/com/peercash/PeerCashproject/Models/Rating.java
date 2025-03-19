package com.peercash.PeerCashproject.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rating")
public class Rating {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private LocalDate qualifyingDate;

    @Column(nullable = false)
    private boolean isReported;

    private String causeForReported;

    private boolean visible;

    @JoinColumn(name = "quialified_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private  User qualified;

 @JoinColumn(name = "quialifying_user_id")
 @ManyToOne(fetch = FetchType.LAZY)
 private  User qualifying;


}
