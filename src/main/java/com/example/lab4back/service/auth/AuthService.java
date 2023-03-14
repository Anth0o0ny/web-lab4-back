package com.example.lab4back.service.auth;

import com.example.lab4back.listening.requests.LoginRequest;
import com.example.lab4back.listening.requests.RegisterRequest;
import com.example.lab4back.listening.responses.JwtResponse;
import com.example.lab4back.listening.responses.MessageResponse;
import com.example.lab4back.model.User;
import com.example.lab4back.repositories.UserRepository;
import com.example.lab4back.security.JwtUtils;
import com.example.lab4back.service.businessLogic.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()
            ));
            String token = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return ResponseEntity.ok().body(new JwtResponse(token, userDetails.getId(), userDetails.getUsername()));
        } catch (BadCredentialsException exception) {
            return ResponseEntity.status(401).body(new MessageResponse("permission denied"));
        } catch (Exception exception) {
            System.out.println("Error in login(): " + exception.getClass());
            return ResponseEntity.status(500).body(new MessageResponse("server error: cannot create user.\nerror class: " + exception.getClass()));
        }
    }

    public ResponseEntity<Object> register(RegisterRequest registerRequest) {
        if (!Validator.checkUsernameAndPassword(registerRequest.getUsername(), registerRequest.getPassword())) {
            return ResponseEntity.status(400).body(new MessageResponse("username and password cannot contains whitespaces"));
        }
        User user = new User();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        userDetails.setUsername(registerRequest.getUsername());
        userDetails.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        try {
            userRepository.save(user);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    registerRequest.getUsername(), registerRequest.getPassword()
            ));
            String token = jwtUtils.generateJwtToken(authentication);
            userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return ResponseEntity.ok().body(new JwtResponse(token, userDetails.getId(), userDetails.getUsername()));
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(403).body(new MessageResponse("user already exists"));
        } catch (Exception exception) {
            System.out.println("Error in register(): " + exception.getClass());
            return ResponseEntity.status(500).body(new MessageResponse("server error: cannot create user.\nerror class: " + exception.getClass()));
        }
    }
}
