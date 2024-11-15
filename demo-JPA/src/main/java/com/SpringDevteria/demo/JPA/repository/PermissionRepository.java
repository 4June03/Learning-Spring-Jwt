package com.SpringDevteria.demo.JPA.repository;

import com.SpringDevteria.demo.JPA.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,String> {
}
