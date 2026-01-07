package com.project.digital_esign.digital_eSign.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",unique = true, nullable = false)
    private Long id;
    @Column(name = "username",unique = true, length = 100)
    private String username;
    @Column(name = "password_hash",length = 255,nullable = true)
    private String password_hash;
    @Column(name = "full_name",length = 100)
    private String full_name;
    @Column(name = "email",length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role role;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<UserKey> user_keys;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<SignatureLog> signature_logs;
}
