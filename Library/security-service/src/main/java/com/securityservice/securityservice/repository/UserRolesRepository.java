package com.securityservice.securityservice.repository;

import com.securityservice.securityservice.model.User;
import com.securityservice.securityservice.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
    List<UserRoles> findByUser(User user);
}
