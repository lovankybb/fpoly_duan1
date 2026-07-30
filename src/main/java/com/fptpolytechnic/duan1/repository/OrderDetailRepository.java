package com.fptpolytechnic.duan1.repository;

import com.fptpolytechnic.duan1.model.Order;
import com.fptpolytechnic.duan1.model.OrderDetail;
import com.fptpolytechnic.duan1.utils.DBContext;

import javax.sql.rowset.JdbcRowSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailRepository {


    /**
     * TABLE order_details
     * id BIGINT PRIMARY KEY IDENTITY(1, 1),
     * order_id BIGINT NOT NULL REFERENCES orders(id),
     * variant_id INT NOT NULL REFERENCES product_variants(id),
     * price DECIMAL(12, 2) NOT NULL,
     * quantity int NOT NULL
     *
     *
     */


    public void insert(List<OrderDetail> orderDetails) throws SQLException {


        if (orderDetails == null || orderDetails.isEmpty()) {
            return;
        }

        String query = """
                INSERT INTO order_details( order_id, variant_id, price, quantity)
                VALUES
                (?, ?, ?, ?)
                """;


        var conn = DBContext.getConnection();
        try (
             var ps = conn.prepareStatement(query);
        ) {
            conn.setAutoCommit(false);

            for (OrderDetail o : orderDetails) {
                ps.setLong(1, o.getOrderId());
                ps.setLong(2, o.getVariantId());
                ps.setBigDecimal(3, o.getPrice());
                ps.setInt(4, o.getQuantity());

                ps.addBatch();
            }

            ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
        }
        finally {
            conn.close();
        }

    }


    public List<OrderDetail> findByOrderId(Long orderId) throws SQLException {

        String query = """
                SELECT * FROM order_details WHERE order_id = ?;
        """;

        try(var conn  = DBContext.getConnection();
            var ps = conn.prepareStatement(query);
        ){

            ps.setLong(1, orderId);
            var rs = ps.executeQuery();
            List<OrderDetail> orderDetails = new ArrayList<>();
            while(rs.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(rs.getLong("order_id"));
                orderDetail.setVariantId(rs.getLong("variant_id"));
                orderDetail.setPrice(rs.getBigDecimal("price"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetails.add(orderDetail);
            }
            return orderDetails;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
