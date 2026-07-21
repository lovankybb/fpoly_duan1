package com.fptpolytechnic.duan1.repository;

import com.fptpolytechnic.duan1.model.ProductVariant;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductVariantRepository {

    public List<ProductVariant> findAll(Long productId, int offset, int limit) {

        List<ProductVariant> productVariants = new ArrayList<>();

        String query = "SELECT * FROM product_variants WHERE prod_id = ? ORDER BY updated_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {

            ps.setLong(1, productId);
            ps.setInt(2, offset);
            ps.setInt(3, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductVariant productVariant = new ProductVariant();
                productVariant.setId(rs.getLong("id"));
                productVariant.setProductId(rs.getLong("prod_id"));
                productVariant.setColorId(rs.getLong("color_id"));
                productVariant.setVersionId(rs.getLong("version_id"));
                productVariant.setPrice(rs.getBigDecimal("price"));
                productVariant.setStock(rs.getInt("stock"));
                productVariant.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                productVariant.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));

                productVariants.add(productVariant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productVariants;
    }


    public ProductVariant findById(Long id) {

        String query = "SELECT * FROM product_variants WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ProductVariant productVariant = new ProductVariant();
                productVariant.setId(rs.getLong("id"));
                productVariant.setProductId(rs.getLong("prod_id"));
                productVariant.setColorId(rs.getLong("color_id"));
                productVariant.setVersionId(rs.getLong("version_id"));
                productVariant.setPrice(rs.getBigDecimal("price"));
                productVariant.setStock(rs.getInt("stock"));
                productVariant.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                productVariant.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                return productVariant;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private ProductVariant returnObject(Long prodId, Long versionId, Long colorId) {
        String query = "SELECT * FROM product_variants WHERE prod_id = ? AND version_id = ? AND color_id = ?";
        try (Connection conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {

            ps.setLong(1, prodId);
            ps.setLong(2, versionId);
            ps.setLong(3, colorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ProductVariant productVariant = new ProductVariant();
                productVariant.setId(rs.getLong("id"));
                productVariant.setProductId(rs.getLong("prod_id"));
                productVariant.setColorId(rs.getLong("color_id"));
                productVariant.setVersionId(rs.getLong("version_id"));
                productVariant.setPrice(rs.getBigDecimal("price"));
                productVariant.setStock(rs.getInt("stock"));
                productVariant.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                productVariant.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                return productVariant;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProductVariant create(ProductVariant productVariant) {

        String query = """
                INSERT INTO product_variants(prod_id, version_id, color_id, price, stock, created_at, updated_at)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (var connection = DBContext.getConnection();
             var ps = connection.prepareStatement(query);
        ) {
            ps.setLong(1, productVariant.getProductId());
            ps.setLong(2, productVariant.getVersionId());
            ps.setLong(3, productVariant.getColorId());
            ps.setBigDecimal(4, productVariant.getPrice());
            ps.setLong(5, productVariant.getStock());
            ps.setObject(6, productVariant.getCreatedAt());
            ps.setObject(7, productVariant.getUpdatedAt());
            ps.executeUpdate();

            return this.returnObject(productVariant.getProductId(), productVariant.getVersionId(), productVariant.getColorId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public ProductVariant update(ProductVariant productVariant) {

        String query = """
                UPDATE product_variants SET
                version_id=?, color_id=?,  price=?, stock=?, updated_at=?
                WHERE id = ?
                """;

        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);) {

            ps.setLong(1, productVariant.getVersionId());
            ps.setLong(2, productVariant.getColorId());
            ps.setBigDecimal(3, productVariant.getPrice());
            ps.setLong(4, productVariant.getStock());
            ps.setObject(5, productVariant.getUpdatedAt());
            ps.setLong(6, productVariant.getId());
            ps.executeUpdate();

            return this.findById(productVariant.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Long id) {
        String query = """
                        DELETE FROM product_variants WHERE id = ?
                """;
        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void deleteByProductId(Long productId) {

        String query = "DELETE FROM product_variants WHERE prod_id = ?";
        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, productId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
