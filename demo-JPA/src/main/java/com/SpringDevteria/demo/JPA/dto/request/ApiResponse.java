package com.SpringDevteria.demo.JPA.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private Integer code = 1000; //Set code khi thành công
    private String message;
    private T result; //Kết quả được trả về

}
