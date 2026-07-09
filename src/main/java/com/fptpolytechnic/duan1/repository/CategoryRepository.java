package com.fptpolytechnic.duan1.repository;


import com.fptpolytechnic.duan1.model.Category;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Category(rs.getLong("id"), rs.getString("name"), rs.getString("description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

   public void add(Category c) {
       String sql = "INSERT INTO categories (name, description) VALUES(?, ?)";
       try (Connection con = DBContext.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
           ps.setString(1, c.getName());
           ps.setString(2, c.getDescription());
           ps.executeUpdate();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   public  void delete(int id) {
       String sql = "DELETE * FROM categories WHERE id = ?";
       try (Connection con = DBContext.getConnection();
       PreparedStatement ps = con.prepareStatement(sql)) {
           ps.setInt(1, id);
           ps.executeUpdate();

       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}
