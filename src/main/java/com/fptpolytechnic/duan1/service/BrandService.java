package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Brand;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BrandService {
    public List<Brand> getAll(){
        List<Brand> list = new ArrayList<>();
        String sql = "SELECT * FROM brands";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()){
                list.add(new Brand(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public void add(Brand b) {
        String sql = "INSERT INTO brands (name, description) VALUES (? , ?)";
        try (Connection con = DBContext.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, b.getName());
            ps.setString(2, b.getDescription());
            ps.executeUpdate();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE * FROM brands WHERE id = ?";
        try (Connection con = DBContext.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
