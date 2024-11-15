package com.SpringDevteria.demo.JPA.service;

import com.SpringDevteria.demo.JPA.dto.request.PermissionRequest;
import com.SpringDevteria.demo.JPA.dto.response.PermissionResponse;
import com.SpringDevteria.demo.JPA.entity.Permission;
import com.SpringDevteria.demo.JPA.mapper.PermissionMapper;
import com.SpringDevteria.demo.JPA.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class PermissionService {
    private PermissionRepository permissionRepository;
    private PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request){
        Permission permission = permissionMapper.toPermission(request); //Map permissionRequest thành permission
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission); //Map lại thành perrmissionResponse để trả về
    }

    public List<PermissionResponse> getAllPermission(){
        var pemissions = permissionRepository.findAll();

        //return permissionMapper.toListPermissionResponse(pemissions); //Trả về dưới dạng response //tự viết
        return pemissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void deletePermission(String permission){
        permissionRepository.deleteById(permission);
    }

}
