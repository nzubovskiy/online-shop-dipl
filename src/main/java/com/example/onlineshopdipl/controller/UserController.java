package com.example.onlineshopdipl.controller;


import com.example.onlineshopdipl.dto.NewPassword;
import com.example.onlineshopdipl.dto.User;
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
    public ResponseEntity<NewPassword> setPassword() {
        return new ResponseEntity<>(HttpStatus.OK);
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
    public ResponseEntity<User> getUser_1() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            tags = "Пользователи",
            summary = "updateUser",
            operationId = "updateUser",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class))
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
    public ResponseEntity<User> reupdateUser() {
        return new ResponseEntity<>(HttpStatus.OK);
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
    public ResponseEntity<User> updateUserImage(
            @Parameter(name = "image", required = true) @RequestPart(value = "image") MultipartFile image) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}