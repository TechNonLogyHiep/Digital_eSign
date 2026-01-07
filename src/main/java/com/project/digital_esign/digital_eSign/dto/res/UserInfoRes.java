package com.project.digital_esign.digital_eSign.dto.res;

import com.project.digital_esign.digital_eSign.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoRes {
    private Long id;
    private String role;
}
