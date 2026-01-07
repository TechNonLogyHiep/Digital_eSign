package com.project.digital_esign.digital_eSign.service;

import com.project.digital_esign.digital_eSign.entity.PassWordResetToken;
import com.project.digital_esign.digital_eSign.entity.RefreshToken;
import com.project.digital_esign.digital_eSign.entity.UserInfo;
import com.project.digital_esign.digital_eSign.repository.PasswordResetRepository;
import com.project.digital_esign.digital_eSign.repository.RefreshTokenRepository;
import com.project.digital_esign.digital_eSign.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder encoder;
    private final EmailService emailService;
    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    @Value("${jwt.reset-password.expiration}")
    private long resetExpiration;

    @Transactional
    public void forgotPassword(String email) {
        UserInfo user = userInfoRepository.findByUsernameOrEmail(email, email)
                .orElseThrow(() -> {return new UsernameNotFoundException("Not found: "+email);});
        passwordResetRepository.deleteByUser(user);

        long timeToMiliS = 5 * 60 * 1000;
        Date expireTimeToMiliS = new Date(System.currentTimeMillis() +  timeToMiliS);
        String token = UUID.randomUUID().toString();

        PassWordResetToken passWordResetToken = new PassWordResetToken();
        passWordResetToken.setToken(token);
        passWordResetToken.setUser(user);
        passWordResetToken.setExpiryDate(expireTimeToMiliS);
        passwordResetRepository.save(passWordResetToken);
        String resetLink = "http://localhost:8080/api/auth/reset-password?token=" + token;
        emailService.sendEmail(user.getEmail(),resetLink,expireTimeToMiliS);

    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        PassWordResetToken resetToken = passwordResetRepository.findByToken(token)
                .orElseThrow(() ->  new UsernameNotFoundException("Invalid token"));
        if(resetToken.getExpiryDate().before(new Date())) {
            passwordResetRepository.delete(resetToken);
            throw new UsernameNotFoundException("Expired token");
        }
        UserInfo user  = resetToken.getUser();
        user.setPassword_hash(encoder.encode(newPassword));
        userInfoRepository.save(user);

        refreshTokenRepository.deleteByUser(user);
        passwordResetRepository.delete(resetToken);
    }

    public void saveRefreshToken(UserInfo user, String refreshToken) {
        refreshTokenRepository.deleteByUser(user);

        RefreshToken token = new RefreshToken();
        token.setToken(refreshToken);
        token.setUser(user);
        token.setExpires(
                new Date(System.currentTimeMillis() + refreshExpiration)
        );
        refreshTokenRepository.save(token);
    }
    public RefreshToken validateRefreshToken(String token){
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).
                orElseThrow(() -> new UsernameNotFoundException("Refresh token not found: " + token));
        if(refreshToken.getExpires().before(new Date())){
            refreshTokenRepository.delete(refreshToken);
            throw new UsernameNotFoundException("Refresh expired : " + token);
        }
        return refreshToken;
    }
}
