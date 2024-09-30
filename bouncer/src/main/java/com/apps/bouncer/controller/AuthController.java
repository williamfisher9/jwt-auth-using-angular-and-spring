package com.apps.bouncer.controller;

import com.apps.bouncer.dao.RoleRepository;
import com.apps.bouncer.dao.UserRepository;
import com.apps.bouncer.dto.LoginRequestDTO;
import com.apps.bouncer.dto.LoginResponseDTO;
import com.apps.bouncer.dto.RegisterRequestDTO;
import com.apps.bouncer.dto.RegisterResponseDTO;
import com.apps.bouncer.exceptions.RoleNotFoundException;
import com.apps.bouncer.model.Role;
import com.apps.bouncer.model.User;
import com.apps.bouncer.security.filter.JwtUtils;
import com.apps.bouncer.service.AuthService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;

    private final AuthService authService;

    @Autowired
    public AuthController(UserRepository repository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, RoleRepository roleRepository, AuthService authService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
        this.authService = authService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public ResponseEntity<RegisterResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO requestDTO){
        return new ResponseEntity<>(authService.registerUser(requestDTO), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginDTO){
        return new ResponseEntity<>(authService.loginUser(loginDTO), HttpStatus.OK);
    }
}
