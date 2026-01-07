package com.project.digital_esign.digital_eSign.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "signature_log")
@AllArgsConstructor
@NoArgsConstructor
public class SignatureLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "signature_log_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @Column(name = "signature_image",columnDefinition = "LONGTEXT")
    private String signatureImage;

    @Column(name = "signature_hash",length = 255)
    private String signatureHash;

    @Column(name = "signed_at")
    private LocalDateTime signedAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SignType signType;

    @Column(name = "ip_address",length = 50)
    private String ipAddress;



}
