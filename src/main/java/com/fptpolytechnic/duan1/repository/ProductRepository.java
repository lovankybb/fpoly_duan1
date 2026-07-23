package com.fptpolytechnic.duan1.repository;


import com.fptpolytechnic.duan1.enums.ProductStatus;
import com.fptpolytechnic.duan1.model.Product;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public Product create(Product product) {

        String query = "INSERT INTO products (name, description, price, sale_price, status, category_id, brand_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);

        ) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice());
            ps.setBigDecimal(4, product.getSalePrice());
            ps.setString(5, product.getStatus().toString());
            ps.setLong(6, product.getCategoryId());
            ps.setLong(7, product.getBrandId());
            ps.setObject(8, product.getCreatedAt());
            ps.setObject(9, product.getUpdatedAt());
            ps.executeUpdate();

            return this.findByName(product.getName());

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }


    public Product findByName(String name) {
        String query = "SELECT * FROM products WHERE name=?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);

        ) {
            ps.setString(1, name);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return this.mapToProduct(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> findAll(int offset, int row) {
        String query = "SELECT * FROM products ORDER BY created_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);

        ) {
            ps.setInt(1, offset);
            ps.setInt(2, row);
            var rs = ps.executeQuery();
            List<Product> products = new java.util.ArrayList<>();
            while (rs.next()) {
                products.add(this.mapToProduct(rs));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Product findById(Long id) {

        String query = "SELECT * FROM products WHERE id=?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return this.mapToProduct(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void delete(Long id) {
        String query = "DELETE FROM products WHERE id=?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product update(Product product) {
        String query = "UPDATE products SET name=?, description=?, price=?, sale_price=?, status=?, updated_at=?, category_id=?, brand_id=? WHERE id=?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice());
            ps.setBigDecimal(4, product.getSalePrice());
            ps.setString(5, product.getStatus().name());
            ps.setObject(6, product.getUpdatedAt());
            ps.setLong(7, product.getCategoryId());
            ps.setLong(8, product.getBrandId());
            ps.setLong(9, product.getId());
            ps.executeUpdate();

            return this.findById(product.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Product> findAllActiveProduct(int offset, int row) {
        String query = "SELECT * FROM products WHERE status='ACTIVE' ORDER BY created_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        List<Product> products = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);

        ) {
            ps.setInt(1, offset);
            ps.setInt(2, row);
            var rs = ps.executeQuery();
            while (rs.next()) {
                products.add(this.mapToProduct(rs));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Product> findNewestProducts() {
        String query = "SELECT TOP 5 * FROM products WHERE status='ACTIVE' ORDER BY updated_at DESC";
        List<Product> products = new java.util.ArrayList<>();

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
        ) {
            var rs = ps.executeQuery();
            while (rs.next()) {
                products.add(this.mapToProduct(rs));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Product findProductByVariantId(Long variantId) {
        String query = """
                SELECT p.id, p.name, p.description, p.price, p.sale_price,
                    p.status, p.category_id, p.brand_id, p.created_at, p.updated_at
                FROM products p
                JOIN product_variants v ON v.product_id = p.id
                WHERE v.id = ?
                """;
        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, variantId);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return this.mapToProduct(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Product mapToProduct(ResultSet rs) throws SQLException {

        return Product.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(rs.getBigDecimal("price"))
                .salePrice(rs.getBigDecimal("sale_price"))
                .status(ProductStatus.valueOf(rs.getString("status")))
                .categoryId(rs.getLong("category_id"))
                .brandId(rs.getLong("brand_id"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();

    }

}

