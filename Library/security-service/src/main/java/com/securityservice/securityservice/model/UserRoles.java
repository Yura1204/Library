package com.securityservice.securityservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sec_user_roles")
public class UserRoles {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // Геттеры и сеттеры
}
