package com.fptpolytechnic.duan1.repository;


import com.fptpolytechnic.duan1.model.Role;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RoleRepository {

    public List<Role> findAll() {


        String query = "SELECT * FROM roles";
        List<Role> roles = new ArrayList<>();

        try (
                Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
        ) {
            // Execute the query and process results
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getLong("id"));
                role.setName(rs.getString("name"));
                role.setDescription(rs.getString("description"));
                roles.add(role);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }

    public Role findByName( String name){
        String query = "SELECT TOP 1 * FROM roles WHERE name = ?";
        Role role = new Role();
        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
        ){
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                role.setId(rs.getLong("id"));
                role.setName(rs.getString("name"));
                role.setDescription(rs.getString("description"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }

    public void setRoleUser(String userId, Long roleId){
        String query = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)";
        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
        ){
            ps.setString(1, userId);
            ps.setLong(2, roleId);
            ps.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<Role> findByUserId(String userId){

        String query = """
                SELECT r.* FROM roles r
                JOIN users_roles ur ON r.id = ur.role_id
                JOIN users u ON ur.user_id = u.id
                WHERE u.id = ?""";


        Set<Role> roles = new java.util.HashSet<>();
        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
        ){
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getLong("id"));
                role.setName(rs.getString("name"));
                role.setDescription(rs.getString("description"));
                roles.add(role);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }
}
