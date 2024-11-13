package com.SpringDevteria.demo.JPA.exception;

import com.SpringDevteria.demo.JPA.dto.request.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class) //Nếu không phải 1 Exception cụ thể thì phương thức này sẽ xử lí
    ResponseEntity<ApiResponse> HandleAnyException(Exception e){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIZE_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZE_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }


    @ExceptionHandler(value = RuntimeException.class) //Tham số value là loại exception muốn bắt
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException e){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(1000);
        apiResponse.setMessage(e.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleValidException(MethodArgumentNotValidException e){ //Phương thức xử lí Validate Exception
        String enumKey = e.getFieldError().getDefaultMessage(); //Trả về message tại Class Validate
        ErrorCode errorCode = ErrorCode.valueOf(enumKey); //Lấy về errorCode với key = message vừa lấy

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse); //trả về message
    }



    @ExceptionHandler(value = AppException.class) //Tham số value là loại exception muốn bắt
    ResponseEntity<ApiResponse> handleAppException(AppException e){ //Phương thức xử lí AppException tự định nghĩa
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(e.getErrorCode().getCode());
        apiResponse.setMessage(e.getErrorCode().getMessage());

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException e){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

}
