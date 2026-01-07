package com.project.digital_esign.digital_eSign.service;

import com.project.digital_esign.digital_eSign.dto.req.RegisterRequest;
import com.project.digital_esign.digital_eSign.entity.Role;
import com.project.digital_esign.digital_eSign.entity.UserInfo;
import com.project.digital_esign.digital_eSign.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserInfoService  {
    private final UserInfoRepository userInfoRepo;
    private final PasswordEncoder encoder;

    public String addUser(UserInfo userInfo){
        userInfo.setPassword_hash(encoder.encode(userInfo.getPassword_hash()));
        userInfoRepo.save(userInfo);
        return "User added successfully";
    }

    public UserInfo register(RegisterRequest req) {
        if(userInfoRepo.findByUsernameOrEmail(req.getUsername(),req.getEmail())!=null){
            throw new UsernameNotFoundException("Username is already in use");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(req.getUsername());
        userInfo.setEmail(req.getEmail());
        userInfo.setPassword_hash(encoder.encode(req.getPassword()));
        userInfo.setFull_name(req.getFullname());

        userInfo.setRole(Role.USER);
        return userInfoRepo.save(userInfo);

    }
}
