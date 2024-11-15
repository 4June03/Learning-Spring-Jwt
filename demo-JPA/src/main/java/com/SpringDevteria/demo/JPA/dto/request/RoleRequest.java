package com.SpringDevteria.demo.JPA.dto.request;

import com.SpringDevteria.demo.JPA.entity.Permission;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoleRequest {
    private String name;
    private String description;
    private Set<String> permissions;
}
