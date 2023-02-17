package com.example.onlineshopdipl.controller;


import com.example.onlineshopdipl.dto.NewPassword;
import com.example.onlineshopdipl.dto.UserDto;
import com.example.onlineshopdipl.entity.User;
import com.example.onlineshopdipl.service.ImageService;
import com.example.onlineshopdipl.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

    private final ImageService imageService;

    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @Operation(
            tags = "Пользователи",
            summary = "setPassword",
            operationId = "setPassword",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NewPassword.class))
            ),
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
                        @Content(mediaType = "*/*", schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }

    )
    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<User> getUser_1(Authentication authentication){
        User user = userService.getMe(authentication.getName());
        return ResponseEntity.ok(user);
    }

    @Operation(
            tags = "Пользователи",
            summary = "updateUser",
            operationId = "updateUser",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content =
                        @Content(mediaType = "*/*", schema = @Schema(implementation = User.class))),
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
        return ResponseEntity.ok(user);
    }

    @Operation(
            operationId = "updateUserImage",
            summary = "updateUserImage",
            tags = { "Пользователи" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = User.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/me/image",
            produces = { "*/*" },
            consumes = { "multipart/form-data" }
    )
    public ResponseEntity<byte[]> updateUserImage(@PathVariable("id") Integer id, @RequestPart MultipartFile image,
                                                 Authentication authentication) throws IOException {

        byte[] imageBytes = imageService.updateUserImage(id, image, authentication);
        return ResponseEntity.ok(imageBytes);
    }
}