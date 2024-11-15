package com.SpringDevteria.demo.JPA.mapper;

import com.SpringDevteria.demo.JPA.dto.request.RoleRequest;
import com.SpringDevteria.demo.JPA.dto.response.RoleResponse;
import com.SpringDevteria.demo.JPA.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions",ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
