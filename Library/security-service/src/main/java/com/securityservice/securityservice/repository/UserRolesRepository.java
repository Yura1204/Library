package com.securityservice.securityservice.repository;

import com.securityservice.securityservice.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
}
