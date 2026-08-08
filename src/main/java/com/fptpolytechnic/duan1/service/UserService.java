package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Role;
import com.fptpolytechnic.duan1.model.User;
import com.fptpolytechnic.duan1.repository.OrderRepository;
import com.fptpolytechnic.duan1.repository.RoleRepository;
import com.fptpolytechnic.duan1.repository.UserRepository;
import com.fptpolytechnic.duan1.utils.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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


    public boolean changePassword(String userName, String oldPassword, String newPassword){
        User user = userRepository.findByUsername(userName);
        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            return false;
        }
        return userRepository.changePassword(user.getId(), passwordEncoder.encode(newPassword));
    }


    public List<User> findAll(){
        return userRepository.findAll();
    }


    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User findById(String id) {
        return userRepository.findById(id);
    }

    public User update(User user, String newPasswordPlain) {
        User existing = userRepository.findById(user.getId());
        if (existing.getId() == null || existing.getId().isBlank()) {
            return null;
        }
        user.setCreatedAt(existing.getCreatedAt());
        user.setUpdatedAt(LocalDateTime.now());
        if (newPasswordPlain != null && !newPasswordPlain.isBlank()) {
            user.setPassword(passwordEncoder.encode(newPasswordPlain));
        } else {
            user.setPassword(existing.getPassword());
        }
        return userRepository.update(user);
    }

    public void delete(String id) {
        roleRepository.deleteByUserId(id);
        userRepository.delete(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean savePersonalInfo(String username, String email, String phone, String address) {
        User existing = userRepository.findByUsername(username);
        
        if(userRepository.updateUserInfo(existing.getId(), address, email, phone))
        return true; 
		return false; 
   }

    public boolean clearPersonalInfo(String username) {
        User existing = userRepository.findByUsername(username);
        if (existing.getId() == null || existing.getId().isBlank()) {
            return false;
        }
        userRepository.clearContactInfo(existing.getId());
        return true;
    }
}


