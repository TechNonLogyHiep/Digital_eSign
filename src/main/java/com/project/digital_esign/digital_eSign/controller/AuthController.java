package com.project.digital_esign.digital_eSign.controller;

import com.project.digital_esign.digital_eSign.dto.req.ForgotPasswordReq;
import com.project.digital_esign.digital_eSign.dto.req.LoginRequest;
import com.project.digital_esign.digital_eSign.dto.req.RegisterRequest;
import com.project.digital_esign.digital_eSign.dto.req.ResetPasswordReq;
import com.project.digital_esign.digital_eSign.dto.res.JwtRespone;
import com.project.digital_esign.digital_eSign.dto.res.UserInfoRes;
import com.project.digital_esign.digital_eSign.entity.UserInfo;
import com.project.digital_esign.digital_eSign.repository.RefreshTokenRepository;
import com.project.digital_esign.digital_eSign.service.JwtService;
import com.project.digital_esign.digital_eSign.service.UserInfoService;
import com.project.digital_esign.digital_eSign.repository.UserInfoRepository;
import com.project.digital_esign.digital_eSign.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final UserInfoService userService;
    private final UserInfoRepository userInfoRepository;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        UserInfo userInfo = userService.register(registerRequest);
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        UserInfo user = userInfoRepository.findByUsernameOrEmail(req.getUsername(),req.getUsername()).orElseThrow(
                () ->{return new UsernameNotFoundException("Not found: "+req.getUsername());}
        );

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        authService.saveRefreshToken(user,refreshToken);
        return ResponseEntity.ok(
                new  JwtRespone(accessToken,refreshToken, new UserInfoRes(user.getId(),user.getRole().name()))
        );
    }
    @PostMapping("/logout")
    @Transactional
    public ResponseEntity<?> logout(Authentication authentication) {
        UserInfo user = userInfoRepository.findByUsernameOrEmail(authentication.getName(),authentication.getName())
                .orElseThrow(() -> {return new UsernameNotFoundException("Not found: "+authentication.getName());});
        refreshTokenRepository.deleteByUser(user);
        return ResponseEntity.ok("Logged out");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordReq req){
        authService.forgotPassword(req.getEmail());
        return ResponseEntity.ok("Please check your email to reset password");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordReq req){
        authService.resetPassword(req.getToken(),req.getNewPassword());
        return ResponseEntity.ok("Password reset successfully");
    }
}
