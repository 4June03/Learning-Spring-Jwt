package com.SpringDevteria.demo.JPA.dto.response;

import com.SpringDevteria.demo.JPA.entity.Permission;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoleResponse {
    private String name;
    private String description;
    private Set<PermissionResponse> permissions;
}
