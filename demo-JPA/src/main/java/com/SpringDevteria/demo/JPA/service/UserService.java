package com.SpringDevteria.demo.JPA.service;

import com.SpringDevteria.demo.JPA.dto.request.UserCreationRequest;
import com.SpringDevteria.demo.JPA.dto.request.UserUpdateRequest;
import com.SpringDevteria.demo.JPA.dto.response.UserDto;
import com.SpringDevteria.demo.JPA.entity.User;
import com.SpringDevteria.demo.JPA.exception.AppException;
import com.SpringDevteria.demo.JPA.exception.ErrorCode;
import com.SpringDevteria.demo.JPA.mapper.UserMapper;
import com.SpringDevteria.demo.JPA.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor //
@FieldDefaults(makeFinal = true) //Các thuộc tính phải là hằng thì RequiredArgsConstructor mới tự động Inject

public class UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    public User createUser(UserCreationRequest request){


        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        //Map UserCreationRequest sang kiểu User
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10); //Tham số là độ mạnh của mã hóa, thường là 10

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword())); //Mã hóa mật khẩu


        return userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public UserDto getUser(String id){
        return userMapper.toUserDto(userRepository.findById(id).orElseThrow( ()->new RuntimeException("User not found") ));
    }

    public UserDto updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found"));
//        user.setPassword(request.getPassword());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());
        //sử dụng mapper để loại bỏ các dòng code dài dòng
        userMapper.updateUser(user, request);

        return userMapper.toUserDto(userRepository.save(user));
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }

}
