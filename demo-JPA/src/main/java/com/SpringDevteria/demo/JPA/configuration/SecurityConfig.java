package com.SpringDevteria.demo.JPA.configuration;

import com.nimbusds.jose.JWSAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {"/users", "/auth/login","/auth/introspect"}; //Khai báo các endpoint

    @Value("${jwt.signerKey}")
    private String signerKey;

    //B1: Cấu hình filterchain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                request->
                        request.requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINTS).permitAll()
                                .anyRequest().authenticated()

        );

        //b2: cấu hình oauth2 để verify token
        //Cấu hình oath2ResourceServer để đăng ký 1 authenticationProvider
        //Khi cung cấp 1 token vào authentication sẽ thực hiện authentication
        http.oauth2ResourceServer(oath2->
                oath2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())) //Khi cấu hình cần cung cấp 1 jwtDecoder
        );

        http.csrf(csrf->csrf.disable()); //

        return http.build();
    }


    //B3: tạo jwtDecoder cho bước 2
    @Bean
    JwtDecoder jwtDecoder(){
        //Lấy ra thông số key (spec = specifications)
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(),"HS512");

        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    };

}
