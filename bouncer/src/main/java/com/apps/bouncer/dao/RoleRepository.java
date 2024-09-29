package com.apps.bouncer.dao;

import com.apps.bouncer.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
