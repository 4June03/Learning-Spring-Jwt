package com.SpringDevteria.demo.JPA.repository;

import com.SpringDevteria.demo.JPA.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
}
