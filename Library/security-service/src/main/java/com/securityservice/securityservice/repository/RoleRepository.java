package com.securityservice.securityservice.repository;

import com.securityservice.securityservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
