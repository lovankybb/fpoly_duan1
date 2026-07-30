package com.fptpolytechnic.duan1.repository;


import com.fptpolytechnic.duan1.enums.OrderStatus;
import com.fptpolytechnic.duan1.enums.PaymentMethod;
import com.fptpolytechnic.duan1.enums.PaymentStatus;
import com.fptpolytechnic.duan1.model.Order;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {



/*
*
 TABLE orders(
    id BIGINT PRIMARY KEY IDENTITY(1001, 1),
    order_code VARCHAR(255) NOT NULL,

    customer_name NVARCHAR(255) NOT NULL,
    customer_address NVARCHAR(255) NOT NULL,
    customer_phone NVARCHAR(255) NOT NULL,
    customer_note NVARCHAR(255) NOT NULL,

    user_id VARCHAR(255)  REFERENCES users(id),

    total_amount DECIMAL(10, 2),

    order_status VARCHAR(255) NOT NULL,
    payment_status VARCHAR(255) NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    paid_at DATETIME,


    created_at DATETIME,
    updated_at DATETIME,

    canceled_at DATETIME,
    cancel_reason NVARCHAR(255),
* */


    public Order create(Order order) {

        String query = """
                INSERT INTO orders( 
                    order_code, user_id,
                    customer_name, customer_address, customer_phone, customer_note,
                    order_status, 
                    payment_status, payment_method,
                    created_at, updated_at )
                VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, order.getOrderCode());
            ps.setString(2, order.getUserId());
            ps.setString(3, order.getCustomerName());
            ps.setString(4, order.getCustomerAddress());
            ps.setString(5, order.getCustomerPhone());
            ps.setString(6, order.getCustomerNote());
            ps.setString(7, order.getOrderStatus().name());
            ps.setString(8, order.getPaymentStatus().name());
            ps.setString(9, order.getPaymentMethod().name());
            ps.setTimestamp(10, Timestamp.valueOf(order.getCreatedAt()));
            ps.setTimestamp(11, Timestamp.valueOf(LocalDateTime.now()));

            ps.executeUpdate();
            return this.returnOrder(order.getOrderCode());

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Order updateTotalAmount(Long orderId, BigDecimal totalAmount) {
        String query = """
                UPDATE orders
                SET total_amount = ?, updated_at=?
                WHERE id= =?
                """;

        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setBigDecimal(1, totalAmount);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(3, orderId);
            ps.executeUpdate();
            return this.returnOrder(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order returnOrder(String orderCode) {

        String query = """
                SELECT * FROM orders WHERE order_code=?
                """;

        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, orderCode);
            var rs = ps.executeQuery();

            if (rs.next()) {
                return this.mapToOrder(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Order returnOrder(Long orderId) {

        String query = """
                SELECT * FROM orders WHERE id=?
                """;

        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, orderId);
            var rs = ps.executeQuery();

            if (rs.next()) {
                return this.mapToOrder(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Order> findAll(int offset, int limit) {

        String query = """
                       SELECT * FROM orders ORDER BY updated DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                """;

        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            var rs = ps.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                orders.add(this.mapToOrder(rs));
            }
            return orders;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {

        String query = """
                UPDATE orders 
                SET order_status=?
                WHERE id=?
                """;
        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, orderStatus.name());
            ps.setLong(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updatePaymentStatus(Long orderId, PaymentStatus paymentStatus) {

        String query = """
                UPDATE orders 
                SET payment_status=?
                WHERE id=?
                """;


        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, paymentStatus.name());
            ps.setLong(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Order mapToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));

//        customer
        order.setOrderCode(rs.getString("order_code"));
        order.setCustomerName(rs.getString("customer_name"));
        order.setCustomerPhone(rs.getString("customer_phone"));
        order.setCustomerAddress(rs.getString("customer_address"));
        order.setUserId(rs.getString("user_id") != null ? rs.getString("user_id") : "Anonymous");


//                Status
        OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
        order.setOrderStatus(orderStatus);

        PaymentMethod paymentMethod = PaymentMethod.valueOf(rs.getString("payment_method"));
        order.setPaymentMethod(paymentMethod);

        PaymentStatus paymentStatus = PaymentStatus.valueOf(rs.getString("payment_status"));
        order.setPaymentStatus(paymentStatus);
        order.setPaidAt(rs.getTimestamp("paid_at").toLocalDateTime());

//                Time
        order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        order.setUpdateAt(rs.getTimestamp("updated_at").toLocalDateTime());

//                Cancel
        order.setCancelledAt(rs.getTimestamp("cancelled_at").toLocalDateTime());
        order.setCancelReason(rs.getString("cancel_reason") == null ? "" : rs.getString("cancel_reason"));
        return order;
    }

    
}

