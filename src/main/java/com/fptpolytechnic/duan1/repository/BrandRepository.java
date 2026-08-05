package com.fptpolytechnic.duan1.repository;

import com.fptpolytechnic.duan1.model.Brand;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrandRepository {

    public List<Brand> getAll() {
        List<Brand> list = new ArrayList<>();
        String sql = "SELECT * FROM brands ORDER BY id ASC";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Brand(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("image_url")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Brand> findNewest() {
        List<Brand> list = new ArrayList<>();
        String sql = "SELECT TOP 5 * FROM brands ORDER BY id DESC";
        try(var conn = DBContext.getConnection();
            var ps = conn.prepareStatement(sql);
        ) {
            var rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Brand(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("image_url")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Brand getById(int id) {
        String sql = "SELECT * FROM brands WHERE id = ?";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Brand(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("image_url")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(Brand b) {
        String sql = "INSERT INTO brands (name, description, image_url) VALUES (?, ?, ?)";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, b.getName());
            ps.setString(2, b.getDescription());
            ps.setString(3, b.getImage());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Brand b) {
        String sql = "UPDATE brands SET name = ?, description = ?, image_url = ? WHERE id = ?";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, b.getName());
            ps.setString(2, b.getDescription());
            ps.setString(3, b.getImage());
            ps.setInt(4, b.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(long id) {
        String sql = "DELETE FROM brands WHERE id = ?";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}