package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.dto.NewPassword;
import com.example.onlineshopdipl.dto.Role;
import com.example.onlineshopdipl.dto.UserDto;
import com.example.onlineshopdipl.entity.Image;
import com.example.onlineshopdipl.entity.User;
import com.example.onlineshopdipl.exception.ImageNotFoundException;
import com.example.onlineshopdipl.exception.UserNoRightsException;
import com.example.onlineshopdipl.mapper.UserMapper;
import com.example.onlineshopdipl.repository.ImageRepository;
import com.example.onlineshopdipl.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public UserDto editUser(UserDto user, String username) {
        Optional<User> optionalUser = Optional.ofNullable(getUser(username));

        optionalUser.ifPresent(userEntity -> {

            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setPhone(user.getPhone());

            userRepository.save(userEntity);
        });
        return userMapper.toDTO(getUser(username));
    }

    public UserDto getMe(String username) {
        User user = userRepository.findUserByUsername(username);
        return userMapper.toDTO(user);
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

    public void updateUserImage(MultipartFile image, Authentication authentication)  {
        if (image.isEmpty()) {
            throw new RuntimeException("Файл пустой");
        }
        User user = getUser(authentication.getName());
        Image ava = imageRepository.findByUserId(user.getId()).orElse(new Image());

        try {
            ava.setImage(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Проблема с загружаемым файлом");
        }
        ava.setUser(user);
        imageRepository.save(ava);

    }

    public byte[] getUserImage(Integer id) {
        Image image = imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
        return image.getImage();
    }
}
