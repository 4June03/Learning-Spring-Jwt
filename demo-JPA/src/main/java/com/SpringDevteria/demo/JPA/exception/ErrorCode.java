package com.SpringDevteria.demo.JPA.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001,"user existed"),
    UNCATEGORIZE_EXCEPTION(99999,"Uncategorized_exception"),
    USERNAME_INVALID(404,"User name must be at least 3 character"),
    INVALID_PASSWORD(999,"Password must be at least 8 character"),
    USER_NOT_EXISTED(1002,"User not existed"),
    UNAUTHENTICATED(1003,"Unauthenticated")
    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
