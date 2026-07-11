package com.example.authService.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name= "Username",nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String Email;

    @Column(name = "Pash_hash", nullable = false)
    private String passwordHash;

    @Column(name = "created_at")
    private String createdAt;
}