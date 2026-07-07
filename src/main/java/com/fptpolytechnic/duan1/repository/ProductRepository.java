package com.fptpolytechnic.duan1.repository;


import com.fptpolytechnic.duan1.enums.ProductStatus;
import com.fptpolytechnic.duan1.model.Product;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductRepository {

    public Product create(Product product) {

        String query = "INSERT INTO products (name, description, price, sale_price, status, category_id, brand_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

        ){
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice());
            ps.setBigDecimal(4, product.getSalePrice());
            ps.setString(5, product.getStatus().getDisplayName());
            ps.setLong(6, product.getCategoryId());
            ps.setLong(7, product.getBrandId());
            ps.setObject(8, product.getCreatedAt());
            ps.setObject(9, product.getUpdatedAt());
            ps.executeUpdate();

            return this.findByName(product.getName());

        }
        catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }


    public Product findByName( String name){
        String query = "SELECT * FROM products WHERE name=?";

        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

        ){
            ps.setString(1, name);
            var rs = ps.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setSalePrice(rs.getBigDecimal("sale_price"));
                product.setStatus(ProductStatus.fromDisplayName(rs.getString("status")));
                product.setCategoryId(rs.getLong("category_id"));
                product.setBrandId(rs.getLong("brand_id"));
                product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                return product;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> findAll(int offset, int row){
        String query = "SELECT * FROM products ORDER BY created_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

        ){
            ps.setInt(1, offset);
            ps.setInt(2, row);
            var rs = ps.executeQuery();
            List<Product> products = new java.util.ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setSalePrice(rs.getBigDecimal("sale_price"));
                product.setStatus(ProductStatus.fromDisplayName(rs.getString("status")));
                product.setCategoryId(rs.getLong("category_id"));
                product.setBrandId(rs.getLong("brand_id"));
                product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                products.add(product);
            }
            return products;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Product findById( Long id){

        String query = "SELECT * FROM products WHERE id=?";

        try(Connection conn = DBContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
        ){
            ps.setLong(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setSalePrice(rs.getBigDecimal("sale_price"));
                product.setStatus(ProductStatus.fromDisplayName(rs.getString("status")));
                product.setCategoryId(rs.getLong("category_id"));
                product.setBrandId(rs.getLong("brand_id"));
                product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                return product;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void delete( Long id){
        String query = "DELETE FROM products WHERE id=?";

        try(Connection connection = DBContext.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)){
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product update(Long id, Product product){
        String query = "UPDATE products SET name=?, description=? price=?, sale_price=?, status=?, updated_at=? WHERE id=?";

        try(Connection conn = DBContext.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);){
           ps.setString(1, product.getName());
           ps.setString(2, product.getDescription());
           ps.setBigDecimal(3, product.getPrice());
           ps.setBigDecimal(4, product.getSalePrice());
           ps.setString(5, product.getStatus().getDisplayName());
           ps.setObject(6, product.getUpdatedAt());
           ps.setLong(7, id);
           ps.executeUpdate();

           return this.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
