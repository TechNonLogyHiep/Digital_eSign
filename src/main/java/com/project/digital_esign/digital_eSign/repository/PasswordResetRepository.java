package com.project.digital_esign.digital_eSign.repository;

import com.project.digital_esign.digital_eSign.entity.PassWordResetToken;
import com.project.digital_esign.digital_eSign.entity.UserInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PassWordResetToken,Long> {
    Optional<PassWordResetToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM PassWordResetToken p WHERE p.user = :user")
    void deleteByUser(UserInfo user);
}
