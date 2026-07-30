package com.fptpolytechnic.duan1.repository;

import com.fptpolytechnic.duan1.model.Permission;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PermissionRepository {


    public List<Permission> findAll(){

        String query = "SELECT * FROM permissions";
        List<Permission> permissions = new ArrayList<>();

        try(
             Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery();
             ){

            while(rs.next()){
                Permission permission = new Permission();
                permission.setId(rs.getLong("id"));
                permission.setName(rs.getString("name"));
                permission.setDescription(rs.getString("description"));
                permissions.add(permission);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return permissions;
    }
}
