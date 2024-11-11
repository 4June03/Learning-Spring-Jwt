package com.SpringDevteria.demo.JPA.mapper;

import com.SpringDevteria.demo.JPA.dto.request.UserCreationRequest;
import com.SpringDevteria.demo.JPA.dto.request.UserUpdateRequest;
import com.SpringDevteria.demo.JPA.dto.response.UserDto;
import com.SpringDevteria.demo.JPA.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring") //ComponeModel="spring" chỉ định sẽ tạo một bean cho phép inject trong các nơi khác
public interface UserMapper {
    User toUser(UserCreationRequest request);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    //sử dụng @Mapping(source="",target="") nếu 2 trường không cùng tên
    //Nếu muốn bỏ mapping field nào thì dùng ignore=true bên trong @Mapping
    UserDto toUserDto(User user);
}
