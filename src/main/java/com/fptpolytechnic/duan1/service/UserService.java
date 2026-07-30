package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Role;
import com.fptpolytechnic.duan1.model.User;
import com.fptpolytechnic.duan1.repository.RoleRepository;
import com.fptpolytechnic.duan1.repository.UserRepository;
import com.fptpolytechnic.duan1.utils.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(){
        userRepository = new UserRepository();
        roleRepository = new RoleRepository();
        passwordEncoder = new PasswordEncoder();
    }



/*
* Create
* - set id
* - hash password
* - set create and update time
* - set role for user
*
*
* */
    public User create(User user){


        String id = UUID.randomUUID().toString();
        user.setId(id);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user = userRepository.create(user);

        Role role = roleRepository.findByName("USER");
        roleRepository.setRoleUser(id, role.getId());

        return user;
    }


    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}


