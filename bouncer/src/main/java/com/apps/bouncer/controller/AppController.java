package com.apps.bouncer.controller;

import com.apps.bouncer.dao.RoleRepository;
import com.apps.bouncer.dao.UserRepository;
import com.apps.bouncer.dto.*;
import com.apps.bouncer.security.filter.JwtUtils;
import com.apps.bouncer.service.AuthService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AppController {

    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;

    private final AuthService authService;

    @Autowired
    public AppController(UserRepository repository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, RoleRepository roleRepository, AuthService authService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
        this.authService = authService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/public/auth/register")
    public ResponseEntity<RegisterResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO requestDTO){
        return new ResponseEntity<>(authService.registerUser(requestDTO), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/public/auth/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginDTO){
        return new ResponseEntity<>(authService.loginUser(loginDTO), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "home")
    public ResponseEntity<String> getAppPublic(){
        return new ResponseEntity<>("PUBLIC CONTENTS", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/app/dashboard")
    public ResponseEntity<UsernameDTO> getAppSecured(@RequestBody UsernameDTO usernameDTO){
        return new ResponseEntity<>(usernameDTO, HttpStatus.OK);
    }

}
