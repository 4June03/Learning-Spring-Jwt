package com.SpringDevteria.demo.JPA.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_EXISTED(1001,"user existed",HttpStatus.BAD_REQUEST),
    UNCATEGORIZE_EXCEPTION(99999,"Uncategorized_exception",HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_INVALID(404,"User name must be at least 3 character",HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(999,"Password must be at least 8 character",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002,"User not existed",HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1003,"Unauthenticated",HttpStatus.UNAUTHORIZED),
    INVALID_KEY(1004,"Uncategorized error",HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1005,"You do not have the permission",HttpStatus.BAD_REQUEST)
    ;
    private int code;
    private String message;
    private HttpStatus status;


}
