package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.dto.NewPassword;
import com.example.onlineshopdipl.dto.Role;
import com.example.onlineshopdipl.dto.UserDto;
import com.example.onlineshopdipl.entity.User;
import com.example.onlineshopdipl.exception.UserNoRightsException;
import com.example.onlineshopdipl.exception.UserNotFoundException;
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

    public UserService(UserRepository userRepository, UserMapper userMapper, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.userMapper=userMapper;
        this.imageRepository = imageRepository;
    }

    public UserDto findUser(Integer id) {
        return userMapper.toDTO(userRepository.findById(id).get());
    }

    public UserDto editUser(UserDto user, String username) {
        Optional<User> optionalUser = Optional.of(getUser(username));

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

    public User getMe(String username) {
        return userRepository.findUserByUsername(username);
    }

    public UserDto changePassword(NewPassword newPassword, String username) {
        User user = userRepository.getUserByPassword(newPassword.getCurrentPassword());
        user.setPassword((newPassword.getNewPassword()));
        if (user != null) {
            return userMapper.toDTO(user);
        }
        return null;
    }

    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void checkUserHaveRights(Authentication authentication, String username) {
        boolean checkUserIsMe = authentication.getName().equals(username);
        boolean checkUserIsAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().contains(Role.ADMIN.name()));
        if (!(checkUserIsAdmin || checkUserIsMe)) {
            throw new UserNoRightsException("You have no rights to perform this operation");
        }
    }
}
