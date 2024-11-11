package com.SpringDevteria.demo.JPA.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthenticationRequest {
    private String username;
    private String password;
}
