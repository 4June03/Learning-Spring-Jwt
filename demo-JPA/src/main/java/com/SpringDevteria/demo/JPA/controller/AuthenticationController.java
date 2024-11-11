package com.SpringDevteria.demo.JPA.controller;

import com.SpringDevteria.demo.JPA.dto.request.ApiResponse;
import com.SpringDevteria.demo.JPA.dto.request.AuthenticationRequest;
import com.SpringDevteria.demo.JPA.dto.response.AuthenticationResponse;
import com.SpringDevteria.demo.JPA.dto.response.UserDto;
import com.SpringDevteria.demo.JPA.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {

        Boolean result = authenticationService.authenticate(request);

        return ApiResponse.<AuthenticationResponse>builder() //Trả về api response
                .result(AuthenticationResponse.builder()
                        .authenticated(result) //khởi tạo authenticationResponse
                        .build())
                .build();
    }


}
