package com.fptpolytechnic.duan1.repository;


import com.fptpolytechnic.duan1.enums.OrderStatus;
import com.fptpolytechnic.duan1.enums.PaymentMethod;
import com.fptpolytechnic.duan1.enums.PaymentStatus;
import com.fptpolytechnic.duan1.model.Order;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
                INSERT INTO orders(customer_name, customer_address, customer_phone, customer_note, user_id, total_amount, order_status, payment_status, payment_method, paid_at, created_at,
                updated_at, canceled_at, cancel_reason
                """;


        return null;
    }


    public Order returnOrder(String orderId) {

        String query = """
                SELECT * FROM ORDER WHERE order_code=?
                """;

        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, orderId);
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
        try(var conn = DBContext.getConnection();
            var ps = conn.prepareStatement(query);
        ){
            ps.setString(1, orderStatus.name());
            ps.setLong(2, orderId);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updatePaymentStatus(Long orderId, PaymentStatus paymentStatus) {

        String query = """
                UPDATE orders 
                SET payment_status=?
                WHERE id=?
                """;


        try(var conn = DBContext.getConnection();
            var ps = conn.prepareStatement(query);
        ){
            ps.setString(1, paymentStatus.name());
            ps.setLong(2, orderId);
            ps.executeUpdate();
        }
        catch (SQLException e) {
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
        order.setPaidAt(LocalDateTime.ofInstant(rs.getDate("paid_at").toInstant(), ZoneId.systemDefault()));

//                Time
        order.setCreatedAt(LocalDateTime.ofInstant(rs.getDate("created_at").toInstant(), ZoneId.systemDefault()));
        order.setUpdateAt(LocalDateTime.ofInstant(rs.getDate("updated_at").toInstant(), ZoneId.systemDefault()));

//                Cancel
        order.setCancelledAt(LocalDateTime.ofInstant(rs.getDate("canceled_at").toInstant(), ZoneId.systemDefault()));
        order.setCancelReason(rs.getString("cancel_reason"));
        return order;
    }

}

