package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.dto.NewPassword;
import com.example.onlineshopdipl.dto.UserDto;
import com.example.onlineshopdipl.entity.User;
import com.example.onlineshopdipl.mapper.UserMapper;
import com.example.onlineshopdipl.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper=userMapper;
    }

    public UserDto editUser(UserDto user) {
        Optional<User> optionalUser = Optional.of(getUserByLogin(user.getEmail()));

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

    public UserDto getMe(Boolean authenticated, String userLogin) {
        return userMapper.toDTO(getUserByLogin(userLogin));
    }

    public UserDto changePassword(NewPassword newPassword) {
        User user = userRepository.getUserByPassword(newPassword.getCurrentPassword());
        user.setPassword((newPassword.getNewPassword()));
        return userMapper.toDTO(user);
    }

    public User getUserByLogin(String userLogin) {
        return userRepository.findByEmail(userLogin);
    }
}
