package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.dto.response.OrderMonthRevenueResponse;
import com.fptpolytechnic.duan1.dto.response.PopularProductResponse;
import com.fptpolytechnic.duan1.model.Order;
import com.fptpolytechnic.duan1.repository.DashBoardRepository;
import com.fptpolytechnic.duan1.repository.OrderRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet({
        "/admin",
        "/admin/dashboard"
})
public class DashboardServlet extends HttpServlet {


    private final DashBoardRepository dashBoardRepository;
    private final OrderRepository orderRepository;

    public DashboardServlet() {
        this.dashBoardRepository = new DashBoardRepository();
        this.orderRepository = new OrderRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int month = LocalDate.now().getMonthValue();

        if(req.getParameter("month") != null && !req.getParameter("month").trim().isEmpty()) {
            if(Integer.valueOf(req.getParameter("month")) >= 1 ||
                    Integer.valueOf(req.getParameter("month")) <= 12
            ) {
                month = Integer.valueOf(req.getParameter("month"));
            }
        }

        System.out.println(month);
        int year = LocalDate.now().getYear();

        System.out.println("INFO: Month: "+ month + " Year: " + year);
        OrderMonthRevenueResponse revenue = dashBoardRepository.getMonthlyRevenue(month, year);
        List<Order> orders = orderRepository.findAll(0, 5, null);
        Long newUserCount = dashBoardRepository.getUserByCurrentMonth();
        Long totalOrder = dashBoardRepository.getTotalOrderCount(month, year);
        Long lowStockCount = dashBoardRepository.getLowStockProductCount();
        List<PopularProductResponse> popularProducts = dashBoardRepository.getTopSellingVariantsByMonth(month, year);


        req.setAttribute("orders", orders);
        req.setAttribute("revenue", revenue);
        req.setAttribute("newUserCount", newUserCount);
        req.setAttribute("totalOrder", totalOrder);
        req.setAttribute("lowStockCount", lowStockCount);
        req.setAttribute("popularProducts", popularProducts);
        req.setAttribute("month", month);
        req.getRequestDispatcher("/views/admin/dashboard.jsp").forward(req, resp);
    }
}
