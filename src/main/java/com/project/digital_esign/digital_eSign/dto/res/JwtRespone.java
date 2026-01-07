package com.project.digital_esign.digital_eSign.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtRespone {
    private String accessToken;
    private String refreshToken;
    private UserInfoRes userInfoRes;
}
