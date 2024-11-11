package com.SpringDevteria.demo.JPA.service;

import com.SpringDevteria.demo.JPA.dto.request.AuthenticationRequest;
import com.SpringDevteria.demo.JPA.entity.User;
import com.SpringDevteria.demo.JPA.exception.AppException;
import com.SpringDevteria.demo.JPA.exception.ErrorCode;
import com.SpringDevteria.demo.JPA.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class AuthenticationService {
    private UserRepository userRepository;

    public Boolean authenticate(AuthenticationRequest request){
        //Lấy ra user
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Boolean isMatched = passwordEncoder.matches(request.getPassword(), user.getPassword()); //kiểm tra pass có match hay không

        return isMatched;
    }

}
