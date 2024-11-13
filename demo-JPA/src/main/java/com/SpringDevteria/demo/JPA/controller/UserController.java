package com.SpringDevteria.demo.JPA.controller;

import com.SpringDevteria.demo.JPA.dto.request.ApiResponse;
import com.SpringDevteria.demo.JPA.dto.request.UserCreationRequest;
import com.SpringDevteria.demo.JPA.dto.request.UserUpdateRequest;
import com.SpringDevteria.demo.JPA.dto.response.UserDto;
import com.SpringDevteria.demo.JPA.entity.User;
import com.SpringDevteria.demo.JPA.mapper.UserMapper;
import com.SpringDevteria.demo.JPA.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


    @PostMapping
    ApiResponse<UserDto> createUser(@RequestBody @Valid UserCreationRequest request){

        ApiResponse<UserDto> apiResponse = new ApiResponse();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<UserDto>> getUsers() {

        var authentication = SecurityContextHolder.getContext().getAuthentication(); //Lấy thông tin đang đăng nhập hiện tại
        log.info("Username: {}",authentication.getName()); //Log username ra màn hình console
        log.info("Roles: {}",authentication.getAuthorities()); //Log role ra
        //Để hiểu rõ hơn thôi, phần này không bắt buộc

        return ApiResponse.<List<UserDto>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserDto> getUser(@PathVariable String userId){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.getUser(userId));
        apiResponse.setMessage("success");
        return apiResponse;

    }

    //Phương thức truyền token để lấy thông tin của chính mình
    @GetMapping("/myinfo")
    ApiResponse<UserDto> myInfo(){
        return ApiResponse.<UserDto>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    UserDto updateUser(@PathVariable String userId ,@RequestBody UserUpdateRequest request){
        return userService.updateUser(userId,request);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
    }

}
