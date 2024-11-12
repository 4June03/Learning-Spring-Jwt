package com.SpringDevteria.demo.JPA.controller;

import com.SpringDevteria.demo.JPA.dto.request.ApiResponse;
import com.SpringDevteria.demo.JPA.dto.request.AuthenticationRequest;
import com.SpringDevteria.demo.JPA.dto.request.IntrospectRequest;
import com.SpringDevteria.demo.JPA.dto.response.AuthenticationResponse;
import com.SpringDevteria.demo.JPA.dto.response.IntrospectResponse;
import com.SpringDevteria.demo.JPA.dto.response.UserDto;
import com.SpringDevteria.demo.JPA.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {

        var result = authenticationService.authenticate(request);

        return ApiResponse.<AuthenticationResponse>builder() //Trả về api response
                .result(result) //AuthenticationResponse
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticateToken(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspectAuthenticate(request); //Lấy ra kết quả xác thực

        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();

    }


}
