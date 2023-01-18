package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.dto.RegisterReq;
import com.example.onlineshopdipl.dto.Role;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
