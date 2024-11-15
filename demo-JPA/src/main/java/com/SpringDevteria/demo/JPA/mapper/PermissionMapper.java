package com.SpringDevteria.demo.JPA.mapper;


import ch.qos.logback.core.model.ComponentModel;
import com.SpringDevteria.demo.JPA.dto.request.PermissionRequest;
import com.SpringDevteria.demo.JPA.dto.response.PermissionResponse;
import com.SpringDevteria.demo.JPA.entity.Permission;
import lombok.Value;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);

    List<PermissionResponse> toListPermissionResponse (List<Permission> permissions);



}
