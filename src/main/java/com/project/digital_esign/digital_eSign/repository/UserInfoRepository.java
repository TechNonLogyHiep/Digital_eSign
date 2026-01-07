package com.project.digital_esign.digital_eSign.repository;

import com.project.digital_esign.digital_eSign.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    Optional<UserInfo> findByUsernameOrEmail(String username, String email);
}
