package com.example.onlineshopdipl.service.impl;

import com.example.onlineshopdipl.dto.NewPassword;
import com.example.onlineshopdipl.dto.RegisterReq;
import com.example.onlineshopdipl.dto.Role;
import com.example.onlineshopdipl.exception.UserNoRightsExcepton;
import com.example.onlineshopdipl.repository.UserRepository;
import com.example.onlineshopdipl.service.AuthService;
import com.example.onlineshopdipl.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;

    private final PasswordEncoder encoder;
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthServiceImpl(UserDetailsManager manager, UserService userService, UserRepository userRepository) {
        this.manager = manager;
        this.userService = userService;
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (manager.userExists(registerReq.getUsername())) {
            return false;
        }
        manager.createUser(
                User.withDefaultPasswordEncoder()
                        .password(registerReq.getPassword())
                        .username(registerReq.getUsername())
                        .roles(role.name())
                        .build()
        );

        com.example.onlineshopdipl.entity.User savedUser = userService.getMe(registerReq.getUsername());
        savedUser.setFirstName(registerReq.getFirstName());
        savedUser.setLastName(registerReq.getLastName());
        savedUser.setPhone(registerReq.getPhone());
        savedUser.setRegDate(LocalDateTime.now());

        userRepository.save(savedUser);

        return true;
    }

    @Override
    public void changePassword(NewPassword newPassword, Authentication authentication) {
        UserDetails userDetails = manager.loadUserByUsername(authentication.getName());
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        if (encoder.matches(newPassword.getCurrentPassword(), encryptedPasswordWithoutEncryptionType)) {
            com.example.onlineshopdipl.entity.User user = userService.getMe(authentication.getName());
            user.setPassword("{bcrypt}" + encoder.encode(newPassword.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new UserNoRightsExcepton("Wrong current password");
        }
    }
}
