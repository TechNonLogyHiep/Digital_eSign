package com.project.digital_esign.digital_eSign.dto.req;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String fullname;
    private String email;

}
