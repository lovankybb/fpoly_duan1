package com.fptpolytechnic.duan1.repository;

import com.fptpolytechnic.duan1.model.Color;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ColorRepository {
    public List<Color> getAll() {
        List<Color> list = new ArrayList<>();
        String sql = "SELECT * FROM colors";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Color(rs.getInt("id"), rs.getString("name"), rs.getString("hex")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void add(Color c) {
        String sql = "INSERT INTO colors (name, hex) VALUES(? , ?)";
        try (Connection con = DBContext.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getHex());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void delete(int id) {
        String sql = "DELETE * FROM colors WHERE id = ?";
        try (Connection con = DBContext.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }


}
