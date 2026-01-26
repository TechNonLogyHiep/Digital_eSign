package com.project.digital_esign.digital_eSign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO {
    private Long id;
    private String title;
    private String status;
    private LocalDateTime uploadedAt;
    private String fileType;
}
