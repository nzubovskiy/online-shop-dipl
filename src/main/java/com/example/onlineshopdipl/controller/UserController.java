package com.example.onlineshopdipl.controller;


import com.example.onlineshopdipl.dto.NewPassword;
import com.example.onlineshopdipl.dto.UserDto;
import com.example.onlineshopdipl.entity.User;
import com.example.onlineshopdipl.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping("/set_password")
    public ResponseEntity<UserDto> setPassword(NewPassword newPassword) {
        UserDto user = userService.changePassword(newPassword);
        return ResponseEntity.ok(user);
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
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser_1(
            @RequestParam(value = "authenticated", required = false) Boolean authenticated,
            @RequestParam(value = "authorities[0].authority", required = false) String authorities0Authority,
            @RequestParam(required = false) Object credentials,
            Object details,
            Object principal
    ) {
        UserDto user = userService.getMe(authenticated);
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
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(UserDto user) {
        user = userService.editUser(user);
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
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/users/me/image",
            produces = { "*/*" },
            consumes = { "multipart/form-data" }
    )
    public ResponseEntity<String> updateUserImage(
            @Parameter(name = "image", required = true) @RequestPart(value = "image") MultipartFile image) {
        String filePath = "";
        return ResponseEntity.ok(String.format("{\"data\":{ \"image\": \"%s\"}}", filePath));
    }
}