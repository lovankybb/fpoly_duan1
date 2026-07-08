package com.fptpolytechnic.duan1.repository;

import com.fptpolytechnic.duan1.model.Version;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VersionRepository {
    public List<Version> getAll() {
        List<Version> list = new ArrayList<>();
        String sql = "SELECT * FROM version";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Version(rs.getInt("id"), rs.getString("name")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void add(Version v) {
        String sql = "INSERT INTO version (name) VALUES(?)";
        try (Connection con = DBContext.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getName());
            ps.executeUpdate();

        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
    public void delete(int id) {
        String sql = "DELETE * FROM version WHERE id = ?";
        try (Connection con = DBContext.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
}
