package com.fptpolytechnic.duan1.repository;

import com.fptpolytechnic.duan1.model.ProductImage;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class ProductImageRepository {

    public void create(ProductImage productImage) {
        String query = "INSERT INTO product_images(product_id, image_url) VALUES (?, ?)";

        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, productImage.getProductId());
            ps.setString(2, productImage.getImageUrl());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteByProductId(Long prodId){

        String query = "DELETE FROM product_images WHERE product_id=?";

        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, prodId);
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ProductImage> findByProductId(Long prodId) {
        String query = "SELECT * FROM product_images WHERE product_id=?";
        List<ProductImage> productImages = new java.util.ArrayList<>();

        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, prodId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductImage productImage = new ProductImage();
                productImage.setId(rs.getLong("id"));
                productImage.setProductId(rs.getLong("product_id"));
                productImage.setImageUrl(rs.getString("image_url"));
                productImages.add(productImage);
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return productImages;
    }
}
