package com.example.onlineshopdipl.controller;

import com.example.onlineshopdipl.dto.LoginReq;
import com.example.onlineshopdipl.dto.RegisterReq;
import com.example.onlineshopdipl.dto.Role;
import com.example.onlineshopdipl.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.onlineshopdipl.dto.Role.USER;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@NoArgsConstructor
public class AuthController {

    private AuthService authService;


    @Operation(
            tags = "Авторизация",
            summary = "login",
            operationId = "login",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            schema = @Schema(type = "object"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        if (authService.login(req.getUsername(), req.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(
            tags = "Авторизация",
            summary = "register",
            operationId = "register",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RegisterReq.class))
           ),
           responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping ("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReq req) {
        Role role = req.getRole() == null ? USER : req.getRole();
        if (authService.register(req, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
