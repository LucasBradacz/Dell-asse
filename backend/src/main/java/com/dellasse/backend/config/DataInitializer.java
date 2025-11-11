package com.dellasse.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dellasse.backend.repositories.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            roleRepository.save(new com.dellasse.backend.models.Role(null, "ADMIN"));
            roleRepository.save(new com.dellasse.backend.models.Role(null, "FUNCIONARIO"));
            roleRepository.save(new com.dellasse.backend.models.Role(null, "BASIC"));
        }
    }
}
