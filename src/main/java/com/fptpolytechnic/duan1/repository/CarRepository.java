package com.fptpolytechnic.duan1.repository;

import com.fptpolytechnic.duan1.model.Cart;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {

    public List<Cart> findByUserId(String userId) {

        List<Cart> carts = new ArrayList<>();
        String query = "SELECT * FROM carts WHERE user_id = ?";

        try (Connection conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                carts.add(this.mapToCart(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carts;
    }

    public Cart findByUserIdAndVariantId(String userId, Long variantId) {

        String query = "SELECT * FROM carts WHERE user_id = ? AND variant_id = ?";

        try (Connection conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, userId);
            ps.setLong(2, variantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return this.mapToCart(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cart create(Cart cart) {

        String query = "INSERT INTO carts(variant_id, user_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, cart.getVariantId());
            ps.setString(2, cart.getUserId());
            ps.setInt(3, cart.getQuantity());
            ps.executeUpdate();

            return this.findByUserIdAndVariantId(cart.getUserId(), cart.getVariantId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateQuantity(String userId, Long variantId, int quantity) {

        String query = "UPDATE carts SET quantity = ? WHERE user_id = ? AND variant_id = ?";

        try (Connection conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setInt(1, quantity);
            ps.setString(2, userId);
            ps.setLong(3, variantId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByUserIdAndVariantId(String userId, Long variantId) {

        String query = "DELETE FROM carts WHERE user_id = ? AND variant_id = ?";

        try (Connection conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, userId);
            ps.setLong(2, variantId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByUserId(String userId) {

        String query = "DELETE FROM carts WHERE user_id = ?";

        try (Connection conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Cart mapToCart(ResultSet rs) throws SQLException {
        return Cart.builder()
                .id(rs.getLong("id"))
                .variantId(rs.getLong("variant_id"))
                .userId(rs.getString("user_id"))
                .quantity(rs.getInt("quantity"))
                .build();
    }
}
