package com.fptpolytechnic.duan1.repository;


import com.fptpolytechnic.duan1.dto.response.OrderMonthRevenueResponse;
import com.fptpolytechnic.duan1.dto.response.PopularProductResponse;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DashBoardRepository {


    public OrderMonthRevenueResponse getMonthlyRevenue(int month, int year) {
        LocalDateTime startDate = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime endDate = startDate.plusMonths(1);

        String query = """
            SELECT SUM(total_amount) AS month_revenue, COUNT(*) AS order_count
            FROM orders o
            WHERE o.payment_status = 'PAID'
              AND o.order_status = 'COMPLETED'
              AND o.created_at >= ?
              AND o.created_at < ?
            """;

        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query)
        ) {
            ps.setTimestamp(1, Timestamp.valueOf(startDate));
            ps.setTimestamp(2, Timestamp.valueOf(endDate));

            var rs = ps.executeQuery();
            if (rs.next()) {
                return OrderMonthRevenueResponse.builder()
                        .monthRevenue(rs.getDouble("month_revenue"))
                        .orderCount(rs.getLong("order_count"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return OrderMonthRevenueResponse.builder()
                .monthRevenue(0.0)
                .orderCount(0L)
                .build();
    }

    public long getUserByCurrentMonth() {
        String query = """
                SELECT COUNT(*) AS user_count
                FROM users u
                WHERE u.created_at >= DATEADD(month, DATEDIFF(month, 0, GETDATE()), 0)
                AND u.created_at <  DATEADD(month, DATEDIFF(month, 0, GETDATE()) + 1, 0)
                """;
        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {

            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("user_count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Placeholder
    }

    public long getLowStockProductCount() {
        String query = """
                SELECT COUNT(*) AS low_stock_count
                FROM product_variants pv
                WHERE pv.stock <= 5
                """;
        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(query);
        ) {

            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("low_stock_count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Placeholder
    }

    public long getTotalOrderCount(int month, int year) {

        LocalDateTime startDate = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime endDate = startDate.plusMonths(1);


        String query = """
                         SELECT COUNT(*) AS order_count
                            FROM orders o
                            WHERE o.created_at >= ?
                                AND o.created_at < ?
                """;
        try (var conn = DBContext.getConnection();

             var ps = conn.prepareStatement(query);
        ) {

            ps.setTimestamp(1, Timestamp.valueOf(startDate));
            ps.setTimestamp(2, Timestamp.valueOf(endDate));

            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("order_count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Placeholder
    }


    public List<PopularProductResponse> getTopSellingVariantsByMonth( int month, int year) {
        List<PopularProductResponse> list = new ArrayList<>();

        LocalDateTime startDate = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime endDate = startDate.plusMonths(1);

        String sql = """
                WITH count_most AS (
                    SELECT
                        od.variant_id,
                        SUM(od.quantity) AS sold
                    FROM order_details od
                    JOIN orders o ON od.order_id = o.id
                    WHERE o.order_status = 'COMPLETED' 
                      AND o.created_at >= ?
                      AND o.created_at < ?
                    GROUP BY od.variant_id
                )
                SELECT 
                    TOP 5
                    p.name + ' (' + v.name + ' - ' + c.name + ')' AS [name], 
                    pv.stock AS stock, 
                    pv.price AS price, 
                    cm.sold AS sold
                FROM count_most cm
                JOIN product_variants pv ON cm.variant_id = pv.id
                JOIN versions v ON v.id = pv.version_id
                JOIN colors c ON c.id = pv.color_id
                JOIN products p ON p.id = pv.prod_id
                ORDER BY cm.sold DESC;
                """;

        try (var conn = DBContext.getConnection();
             var ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(startDate));
            ps.setTimestamp(2, Timestamp.valueOf(endDate));

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    PopularProductResponse dto = new PopularProductResponse();
                    dto.setName(rs.getString("name"));
                    dto.setStock(rs.getInt("stock"));
                    dto.setPrice(rs.getBigDecimal("price").doubleValue());
                    dto.setSold(rs.getInt("sold"));
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}

