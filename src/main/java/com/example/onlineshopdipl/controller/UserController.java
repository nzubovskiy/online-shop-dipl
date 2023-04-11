package com.example.onlineshopdipl.controller;


import com.example.onlineshopdipl.dto.NewPassword;
import com.example.onlineshopdipl.dto.UserDto;
import com.example.onlineshopdipl.service.ImageService;
import com.example.onlineshopdipl.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            tags = "Пользователи",
            summary = "setPassword",
            operationId = "setPassword",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content =
                        @Content(mediaType = "*/*", schema = @Schema(implementation = NewPassword.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/set_password")
    public ResponseEntity<UserDto> setPassword(@RequestBody NewPassword newPassword, Authentication authentication) {
        UserDto userDto = userService.changePassword(newPassword, authentication.getName());
        if (userDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto);
    }

    @Operation(
            tags = "Пользователи",
            summary = "getUser",
            operationId = "getUser_1",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content =
                        @Content(mediaType = "*/*", schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }

    )
    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser_1(Authentication authentication){
        return ResponseEntity.ok(userService.getMe(authentication.getName()));
    }

    @Operation(
            tags = "Пользователи",
            summary = "updateUser",
            operationId = "updateUser",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content =
                        @Content(mediaType = "*/*", schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, Authentication authentication) {
        UserDto user = userService.editUser(userDto, authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @Operation(
            operationId = "updateUserImage",
            summary = "updateUserImage",
            tags = { "Пользователи" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = UserDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)


    public ResponseEntity<Void> updateUserImage(@org.springframework.web.bind.annotation.RequestBody MultipartFile image,
                                                 Authentication authentication)  {

        userService.updateUserImage(image, authentication);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "getUserAvatar",
            responses = {@ApiResponse(
                    responseCode = "200"),
                    @ApiResponse(responseCode = "404", content = @Content)
            })
    @GetMapping(value = "/avatar/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getUserImage(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserImage(id));
    }
}