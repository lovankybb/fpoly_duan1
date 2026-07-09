package com.fptpolytechnic.duan1.repository;

import com.fptpolytechnic.duan1.model.InvalidateToken;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;

public class InvalidateTokenRepository {

    public InvalidateToken insert(InvalidateToken invalidateToken) {

        String sql = "INSERT INTO invalidated_tokens (jwt_id, expired_at) VALUES (?, ?)";

        try (Connection conn = DBContext.getConnection();
             var ps = conn.prepareStatement(sql)) {

            ps.setString(1, invalidateToken.getJwtId());
            ps.setDate(2, new java.sql.Date(invalidateToken.getExpiredAt().getTime()));

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return invalidateToken;
    }


    public boolean isExist(String jwtId) {
        String sql = "SELECT * FROM invalidated_tokens WHERE jwt_id = ?";
        try (Connection conn = DBContext.getConnection();
             var ps = conn.prepareStatement(sql);

        ) {
            ps.setString(1, jwtId);
            var rs = ps.executeQuery();
            if (rs.next()) return true;
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
