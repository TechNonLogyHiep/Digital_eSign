package com.project.digital_esign.digital_eSign.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_keys")
@AllArgsConstructor
@NoArgsConstructor
public class UserKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;


    @Column(name = "public_key",columnDefinition = "TEXT")
    private String public_key;
    @Column(name = "private_key",columnDefinition = "TEXT")
    private String private_key;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name="active")
    private Boolean active;

}
