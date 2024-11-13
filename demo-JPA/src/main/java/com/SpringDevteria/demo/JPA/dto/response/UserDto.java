package com.SpringDevteria.demo.JPA.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {

    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    Set<String> roles;
}
