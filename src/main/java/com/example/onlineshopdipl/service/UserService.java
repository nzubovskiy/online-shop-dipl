package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.dto.NewPassword;
import com.example.onlineshopdipl.dto.Role;
import com.example.onlineshopdipl.dto.UserDto;
import com.example.onlineshopdipl.entity.Image;
import com.example.onlineshopdipl.entity.User;
import com.example.onlineshopdipl.mapper.UserMapper;
import com.example.onlineshopdipl.repository.ImageRepository;
import com.example.onlineshopdipl.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageRepository imageRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper,
                       ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.userMapper=userMapper;
        this.imageRepository = imageRepository;
    }

    public UserDto findUser(Integer id) {
        return userMapper.toDTO(userRepository.findById(id).get());
    }

    public UserDto editUser(UserDto user, String userLogin) {
        Optional<User> optionalUser = Optional.of(getUserByLogin(userLogin));

        optionalUser.ifPresent(userEntity -> {
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setPhone(user.getPhone());

            userRepository.save(userEntity);
        });
        return optionalUser
                .map(userMapper::toDTO)
                .orElse(null);
    }

    public User getMe(String userLogin) {
        return userRepository.findByEmail(userLogin);
    }

    public UserDto changePassword(NewPassword newPassword, String userLogin) {
        User user = userRepository.getUserByPassword(newPassword.getCurrentPassword());
        user.setPassword((newPassword.getNewPassword()));
        if (user != null) {
            return userMapper.toDTO(user);
        }
        return null;
    }

    public User getUserByLogin(String userLogin) {
        return userRepository.findByEmail(userLogin);
    }

    public boolean checkUserIsAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().contains(Role.ADMIN.name()));
    }

    public boolean checkUserIsMe(Authentication authentication) {
        return authentication.isAuthenticated();
    }

}
