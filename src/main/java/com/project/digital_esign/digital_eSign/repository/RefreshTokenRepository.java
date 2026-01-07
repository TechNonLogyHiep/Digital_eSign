package com.project.digital_esign.digital_eSign.repository;

import com.project.digital_esign.digital_eSign.entity.RefreshToken;
import com.project.digital_esign.digital_eSign.entity.UserInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {
    Optional<RefreshToken> findByToken(String token);

    @Transactional
    void deleteByUser(UserInfo user);
}
