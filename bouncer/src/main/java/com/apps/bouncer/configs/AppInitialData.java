package com.apps.bouncer.configs;

import com.apps.bouncer.dao.RoleRepository;
import com.apps.bouncer.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AppInitialData implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Autowired
    public AppInitialData(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        roleRepository.deleteAll();

        Role adminRole = new Role();
        Role userRole = new Role();
        adminRole.setRole("ADMIN");
        userRole.setRole("USER");

        roleRepository.save(adminRole);
        roleRepository.save(userRole);
    }

}
