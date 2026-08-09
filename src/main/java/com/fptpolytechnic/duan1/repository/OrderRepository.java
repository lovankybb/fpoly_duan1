package com.fptpolytechnic.duan1.repository;


import com.fptpolytechnic.duan1.dto.response.OrderHistoryResponse;
import com.fptpolytechnic.duan1.dto.response.UserSpendResponse;
import com.fptpolytechnic.duan1.enums.OrderStatus;
import com.fptpolytechnic.duan1.enums.PaymentMethod;
import com.fptpolytechnic.duan1.enums.PaymentStatus;
import com.fptpolytechnic.duan1.model.Order;
import com.fptpolytechnic.duan1.utils.DBContext;
import com.oracle.wls.shaded.org.apache.xpath.operations.Or;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

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
                WHERE id=?
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


    public List<Order> findAll(int offset, int limit, String statusName) {

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM orders ");


        boolean isExistStatus = false;
        if (statusName != null &&  !statusName.trim().isEmpty()) {
            queryBuilder.append("WHERE order_status=? ORDER BY created_at ");
            if (!statusName.equals(OrderStatus.PENDING.name())) {
                queryBuilder.append("DESC ");
            } else {
                queryBuilder.append("ASC ");
            }
            isExistStatus = true;
        } else {
            queryBuilder.append("ORDER BY created_at DESC ");
        }
        queryBuilder.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        String query = queryBuilder.toString();

        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            int count = 1;
            if (isExistStatus) {
                ps.setString(count++, statusName);
            }
            ps.setInt(count++, offset);
            ps.setInt(count, limit);
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
                SET order_status=?, updated_at=GETDATE()
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
                SET payment_status=?, paid_at=GETDATE(), updated_at=GETDATE()
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

    public void updateCancelInfo(Long orderId, OrderStatus orderStatus, String cancelReason) {

        String query = """
                UPDATE orders 
                SET order_status=?, canceled_at=?, cancel_reason=?, updated_at=?
                WHERE id=?
                """;
        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            LocalDateTime now = LocalDateTime.now();
            ps.setString(1, orderStatus.name());
            ps.setTimestamp(2, Timestamp.valueOf(now));
            ps.setString(3, cancelReason);
            ps.setTimestamp(4, Timestamp.valueOf(now));
            ps.setLong(5, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCompleteInfo(Long orderId, OrderStatus orderStatus, PaymentStatus paymentStatus, LocalDateTime paidAt) {

        String query = """
                UPDATE orders
                SET order_status=?, payment_status=?, paid_at=?, updated_at=GETDATE()
                WHERE id=?
                """;
        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, orderStatus.name());
            ps.setString(2, paymentStatus.name());
            ps.setTimestamp(3, paidAt == null ? null : Timestamp.valueOf(paidAt));
            ps.setLong(4, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countOrderByUserId(String userId) {
        String query = """
                        SELECT COUNT(*) FROM orders WHERE user_id=?
                """;

        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {

            ps.setString(1, userId);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public UserSpendResponse countCompletedOrderByUserId(String userId) {
        String query = """
                        SELECT COUNT(*) AS completed_order, SUM(total_amount) AS total_spend FROM orders WHERE user_id=? AND order_status='COMPLETED'
                """;

        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {

            ps.setString(1, userId);
            var rs = ps.executeQuery();
            if (rs.next()) {
                UserSpendResponse userSpendResponse = new UserSpendResponse();
                userSpendResponse.setCompletedOrder(rs.getInt("completed_order"));
                userSpendResponse.setTotalSpend(rs.getDouble("total_spend"));
                return userSpendResponse;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void delete(Long orderId) {
        String query = """
                        DELETE FROM orders WHERE id=?
                """;
        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, orderId);
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
        order.setCustomerNote(rs.getString("customer_note"));
        order.setUserId(rs.getString("user_id") != null ? rs.getString("user_id") : "Anonymous");


//                Status
        OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
        order.setOrderStatus(orderStatus);

        PaymentMethod paymentMethod = PaymentMethod.valueOf(rs.getString("payment_method"));
        order.setPaymentMethod(paymentMethod);

        PaymentStatus paymentStatus = PaymentStatus.valueOf(rs.getString("payment_status"));
        order.setPaymentStatus(paymentStatus);
        order.setTotalAmount(rs.getBigDecimal("total_amount") == null ? BigDecimal.ZERO : rs.getBigDecimal("total_amount"));

        order.setPaidAt(rs.getTimestamp("paid_at") == null ? null : rs.getTimestamp("paid_at").toLocalDateTime());

//                Time
        order.setCreatedAt(rs.getTimestamp("created_at") == null ? null : rs.getTimestamp("created_at").toLocalDateTime());
        order.setUpdateAt(rs.getTimestamp("updated_at") == null ? null : rs.getTimestamp("updated_at").toLocalDateTime());

//                Cancel
        order.setCancelledAt(rs.getTimestamp("canceled_at") == null ? null : rs.getTimestamp("canceled_at").toLocalDateTime());
        order.setCancelReason(rs.getString("cancel_reason") == null ? "" : rs.getString("cancel_reason"));
        return order;
    }


//    History

    public List<OrderHistoryResponse> getHistoryByUserId(String userId, int offset, int limit) {

        String query = """
                WITH FirstProductImage AS (
                    SELECT
                        product_id,
                        image_url,
                        ROW_NUMBER() OVER (PARTITION BY product_id ORDER BY id ASC) AS rn
                    FROM product_images
                )
                SELECT
                    od.id AS o_detail_id,
                    o.order_code AS order_code,
                    p.name AS name,
                    v.name AS version_name,
                    c.name AS color_name,
                    o.order_status AS order_status,
                    o.payment_status AS payment_status,
                    pdi.image_url,
                    od.quantity,
                    od.price,
                    o.created_at
                FROM order_details od
                         JOIN orders o ON od.order_id = o.id
                         JOIN product_variants pv ON od.variant_id = pv.id
                         JOIN products p ON pv.prod_id = p.id
                         JOIN colors c ON pv.color_id = c.id
                         JOIN versions v ON pv.version_id = v.id
                         LEFT JOIN FirstProductImage pdi ON p.id = pdi.product_id AND pdi.rn = 1
                WHERE o.user_id = ?
                ORDER BY o.created_at DESC
                OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                """;


        List<OrderHistoryResponse> histories = new ArrayList<>();

        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, userId);
            ps.setInt(2, offset);
            ps.setInt(3, limit);
            var rs = ps.executeQuery();
            while (rs.next()) {
                histories.add(OrderHistoryResponse.builder()
                        .oderDetailId(rs.getLong("o_detail_id"))
                        .orderCode(rs.getString("order_code"))
                        .name(rs.getString("name"))
                        .version(rs.getString("version_name"))
                        .color(rs.getString("color_name"))
                        .imageUrl(rs.getString("image_url"))
                        .quantity(rs.getInt("quantity"))
                        .price(rs.getDouble("price"))
                        .orderStatus(rs.getString("order_status"))
                        .paymentStatus(rs.getString("payment_status"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime().toLocalDate())
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return histories;
    }
}

