package com.ecommerce.happyshopping.repositories;

import com.ecommerce.happyshopping.model.AppRole;
import com.ecommerce.happyshopping.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}