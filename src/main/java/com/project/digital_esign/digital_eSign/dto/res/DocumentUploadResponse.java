package com.project.digital_esign.digital_eSign.dto.res;

import com.project.digital_esign.digital_eSign.entity.DocumentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentUploadResponse {
    private Long documentId;
    private String title;
    private DocumentStatus status;
    private String message;
}
