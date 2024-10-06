package com.apps.bouncer.service;

import com.apps.bouncer.dto.LoginRequestDTO;
import com.apps.bouncer.dto.LoginResponseDTO;
import com.apps.bouncer.dto.RegisterRequestDTO;
import com.apps.bouncer.dto.RegisterResponseDTO;
import com.apps.bouncer.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthService {
    LoginResponseDTO loginUser(LoginRequestDTO requestDTO);
    RegisterResponseDTO registerUser(RegisterRequestDTO requestDTO);
    List<Role> getRoles();
}
