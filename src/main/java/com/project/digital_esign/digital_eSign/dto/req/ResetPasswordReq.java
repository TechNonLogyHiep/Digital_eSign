package com.project.digital_esign.digital_eSign.dto.req;

import lombok.Data;

@Data
public class ResetPasswordReq {
    private String token;
    private String newPassword;
}
