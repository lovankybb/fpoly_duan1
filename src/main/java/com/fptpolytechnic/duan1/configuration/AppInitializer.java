package com.fptpolytechnic.duan1.configuration;

import com.fptpolytechnic.duan1.model.Role;
import com.fptpolytechnic.duan1.model.User;
import com.fptpolytechnic.duan1.repository.RoleRepository;
import com.fptpolytechnic.duan1.repository.UserRepository;
import com.fptpolytechnic.duan1.utils.PasswordEncoder;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.UUID;

@WebListener
public class AppInitializer implements ServletContextListener {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AppInitializer(){
        userRepository = new UserRepository();
        passwordEncoder = new PasswordEncoder();
        roleRepository = new RoleRepository();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {


        if(userRepository.existsByUsername("admin")){
            return;
        }
        String hashedPassword = passwordEncoder.encode("admin123");
        String id = UUID.randomUUID().toString();
        User user = User.builder()
                .id(id)
                .username("admin")
                .password(hashedPassword)
                .build();
        user = userRepository.create(user);
        System.out.println("Admin user created: " + user + "with password: admin123");

        Role role = roleRepository.findByName("ADMIN");
        roleRepository.setRoleUser(id, role.getId());
        System.out.println("Admin role assigned to user: " + user.getUsername());

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup logic here
    }
}
