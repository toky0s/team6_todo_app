package com.example.todolist.Controllers;

import com.example.todolist.Models.Entities.CustomUserDetail;
import com.example.todolist.Models.Requests.LoginRequest;
import com.example.todolist.Models.Requests.UserRequest;
import com.example.todolist.Models.Responses.LoginResponse;
import com.example.todolist.Models.Responses.UserResponse;
import com.example.todolist.Services.UserService;
import com.example.todolist.Utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetail) authentication.getPrincipal());
        Integer userId = userService.getUserId(loginRequest.getUsername());
        return new LoginResponse(jwt, loginRequest.getUsername(), userId.toString());
    }

    @PostMapping("/sign-up")
    public UserResponse createNewUser(@RequestBody UserRequest userRequest){
        return userService.createUser(userRequest);
    }
}
