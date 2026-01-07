package com.project.digital_esign.digital_eSign.service;

import com.project.digital_esign.digital_eSign.entity.UserInfo;
import com.project.digital_esign.digital_eSign.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserInfoRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        UserInfo user = userRepo.findByUsernameOrEmail(identifier,identifier)
                .orElseThrow(() -> new UsernameNotFoundException("Email Not Found: " + identifier));
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + identifier);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword_hash())
                .authorities(user.getRole().name())
                .build();
    }
}
