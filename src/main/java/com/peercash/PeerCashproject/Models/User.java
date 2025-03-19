package com.peercash.PeerCashproject.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String document;

    @Column(nullable = false)
    private LocalDate birthday;

    private boolean active;

    private String typeUser;

    private String bankAccount;
   @ManyToMany
   @JoinTable(name="role_users",
           joinColumns={@JoinColumn(name="user_id")},
           inverseJoinColumns={@JoinColumn(name="role_id")})
    private List<Role> roles;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "qualified",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratingsQualified;

    @OneToMany(mappedBy = "qualifying",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratingsQualifying;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transactions> transactions;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<AuditEntity> auditEntities;
}
