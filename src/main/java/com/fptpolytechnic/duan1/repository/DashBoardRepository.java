package com.fptpolytechnic.duan1.repository;


import com.fptpolytechnic.duan1.dto.response.OrderMonthRevenueResponse;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.math.BigDecimal;

public class DashBoardRepository {


    public OrderMonthRevenueResponse getMonthlyRevenue() {
        String query = """
                SELECT SUM(total_amount) AS month_revenue, COUNT(*) AS order_count
                FROM orders o
                WHERE o.payment_status = 'PAID'
                AND o.order_status = 'COMPLETED'
                AND o.created_at >= DATEADD(month, DATEDIFF(month, 0, GETDATE()), 0)
                AND o.created_at <  DATEADD(month, DATEDIFF(month, 0, GETDATE()) + 1, 0)
                """;
        try(var conn = DBContext.getConnection();
            var ps = conn.prepareStatement(query);
        ) {

           var rs = ps.executeQuery();
           if(rs.next()) {
               return OrderMonthRevenueResponse.builder()
                       .monthRevenue(rs.getDouble("month_revenue"))
                       .orderCount(rs.getLong("order_count"))
                       .build();
           }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return OrderMonthRevenueResponse.builder()
                .monthRevenue(0.0)
                .orderCount(0)
                .build(); // Placeholder
    }

    public long getUserByCurrentMonth() {
        String query = """
                SELECT COUNT(*) AS user_count
                FROM users u
                WHERE u.created_at >= DATEADD(month, DATEDIFF(month, 0, GETDATE()), 0)
                AND u.created_at <  DATEADD(month, DATEDIFF(month, 0, GETDATE()) + 1, 0)
                """;
        try(var conn = DBContext.getConnection();
            var ps = conn.prepareStatement(query);
        ) {

            var rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getLong("user_count");
            }
        }
        catch(Exception e) {
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
        try(var conn = DBContext.getConnection();
            var ps = conn.prepareStatement(query);
        ) {

            var rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getLong("low_stock_count");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return 0; // Placeholder
    }

    public long getTotalOrderCount() {
        String query = """
             SELECT COUNT(*) AS order_count
                FROM orders o
                WHERE MONTH(o.created_at) = MONTH(GETDATE()) 
                     AND YEAR(o.created_at) = YEAR(GETDATE())
    """;
        try(var conn = DBContext.getConnection();
            var ps = conn.prepareStatement(query);
        ) {

            var rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getLong("order_count");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return 0; // Placeholder
    }

}
