package com.SpringDevteria.demo.JPA.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PermissionResponse {
    private String name;
    private String description;
}
