package com.example.authService.Controllers;

import com.example.authService.Request.UserRequest;
import com.example.authService.Response.AuthResponse;
import com.example.authService.entities.User;
import com.example.authService.security.JWTTokenProvider;
import com.example.authService.services.RefreshTokenService;
import com.example.authService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public UserService userService;

    @Autowired
    public RefreshTokenService refreshTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserRequest loginRequest){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String JwtToken = jwtTokenProvider.generateJWTToken(auth);
        User user = userService.getUserByName(loginRequest.getUsername());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("login seccessfully");
        authResponse.setAccessToken("Bearer" + JwtToken);
        authResponse.setUserId(Long.valueOf(user.getId()));
        authResponse.setRefreshToken(refreshTokenService.CreateRefreshToken(user));
         return authResponse;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest userRequest){
         AuthResponse authResponse = new AuthResponse();
         if(userService.getUserByName(userRequest.getUsername()) != null){
             authResponse.setMessage("user name already exist");
             return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
         }
         User user = new User();
         user.setUsername(userRequest.getUsername());
         user.setEmail(userRequest.getEmail());
         user.setCreatedAt(LocalDateTime.now().toString());


         user.setPasswordHash(passwordEncoder.encode(userRequest.getPassword()));
         userService.createUser(user);



        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userRequest.getUsername(),userRequest.getPassword());
        System.out.println("system rach to auth token" + authToken);
        Authentication auth = authenticationManager.authenticate(authToken);
        System.out.println("auth" + auth);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String JwtToken = jwtTokenProvider.generateJWTToken(auth);
        System.out.println("jwt token" + JwtToken);

        authResponse.setMessage("User created Successfully");
        authResponse.setAccessToken("Bearer"+JwtToken);
        authResponse.setRefreshToken(refreshTokenService.CreateRefreshToken(user));

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @GetMapping("/test/restrict")
    public String RestrictedRoute(){
        return "restricted route";
    }
}
