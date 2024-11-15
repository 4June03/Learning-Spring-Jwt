package com.SpringDevteria.demo.JPA.service;

import com.SpringDevteria.demo.JPA.dto.request.RoleRequest;
import com.SpringDevteria.demo.JPA.dto.response.RoleResponse;
import com.SpringDevteria.demo.JPA.mapper.RoleMapper;
import com.SpringDevteria.demo.JPA.repository.PermissionRepository;
import com.SpringDevteria.demo.JPA.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class RoleService {
    private RoleRepository roleRepository;
    private RoleMapper roleMapper;
    private PermissionRepository permissionRepository;


    public RoleResponse createRole(RoleRequest request){
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());//Lấy ra tất cả các permission của role
        role.setPermissions(new HashSet<>(permissions)); //Truyền tất cả permission vào role

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAllRoles(){
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteRole(String role){
        roleRepository.deleteById(role);
    }

}
