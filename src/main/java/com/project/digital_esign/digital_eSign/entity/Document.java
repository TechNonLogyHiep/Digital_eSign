package com.project.digital_esign.digital_eSign.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "documents")
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id",unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    @Column(name = "file_name",length = 255)
    private String file_name;

    @Column(name = "file_path",length = 200)
    private String file_path;

    @Column(name = "file_type",length = 50)
    private String file_type;

    @Column(name = "uploaded_at")
    private LocalDateTime uploaded_at;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private DocumentStatus status = DocumentStatus.PENDING;

}
