package com.apps.bouncer.service;

import com.apps.bouncer.dao.RoleRepository;
import com.apps.bouncer.dao.UserRepository;
import com.apps.bouncer.dto.LoginRequestDTO;
import com.apps.bouncer.dto.LoginResponseDTO;
import com.apps.bouncer.dto.RegisterRequestDTO;
import com.apps.bouncer.dto.RegisterResponseDTO;
import com.apps.bouncer.exceptions.DuplicateUsernameException;
import com.apps.bouncer.exceptions.RoleNotFoundException;
import com.apps.bouncer.model.Role;
import com.apps.bouncer.model.User;
import com.apps.bouncer.security.filter.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO loginDTO) {
        LoginResponseDTO responseDTO = new LoginResponseDTO();


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String token = jwtUtils.generateToken(authentication);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setMessage(token);
        loginResponseDTO.setStatus(200);

        return loginResponseDTO;
    }

    @Override
    public RegisterResponseDTO registerUser(RegisterRequestDTO requestDTO) {

        if(userRepository.findByUsername(requestDTO.getUsername()).isPresent()){
            throw new DuplicateUsernameException("Email address " + requestDTO.getUsername() +" already used!");
        }

        Set<String> roles = requestDTO.getRoles();
        List<Role> dbRoles = roleRepository.findAll();
        Set<Role> toBeSavedRoles = new HashSet<>();

        for(String roleName : roles){
            Role role = null;
            for(Role role1 : dbRoles){
                if(role1.getAuthority().equalsIgnoreCase(roleName)){
                    role = role1;
                }
            }

            if(role == null){
                throw new RoleNotFoundException(roleName + " role not found exception.");
            } else {
                toBeSavedRoles.add(role);
            }
        }

        RegisterResponseDTO responseDTO = new RegisterResponseDTO();

        User user = modelMapper.map(requestDTO ,User.class);

        user.setRoles(toBeSavedRoles);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonExpired(true);

        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        userRepository.save(user);

        responseDTO.setMessage("SUCCESS");
        responseDTO.setStatus(200);

        return responseDTO;
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}