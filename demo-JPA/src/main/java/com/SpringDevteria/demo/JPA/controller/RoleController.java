package com.SpringDevteria.demo.JPA.controller;


import com.SpringDevteria.demo.JPA.dto.request.ApiResponse;
import com.SpringDevteria.demo.JPA.dto.request.PermissionRequest;
import com.SpringDevteria.demo.JPA.dto.request.RoleRequest;
import com.SpringDevteria.demo.JPA.dto.response.PermissionResponse;
import com.SpringDevteria.demo.JPA.dto.response.RoleResponse;
import com.SpringDevteria.demo.JPA.service.PermissionService;
import com.SpringDevteria.demo.JPA.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class RoleController {
    private RoleService roleService;
    private PermissionService permissionService;

    @PostMapping
    public ApiResponse<RoleResponse> createRoles(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllPermissions(){
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRoles())
                .build();
    }

    @DeleteMapping("/{role}")
    public ApiResponse<Void> deletePermission(@PathVariable String role){
        roleService.deleteRole(role);
        return ApiResponse.<Void>builder()
                .message("Delete successfully")
                .build();
    }


}
