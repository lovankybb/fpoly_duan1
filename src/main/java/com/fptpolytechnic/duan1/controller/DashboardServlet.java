package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.dto.response.OrderMonthRevenueResponse;
import com.fptpolytechnic.duan1.model.Order;
import com.fptpolytechnic.duan1.repository.DashBoardRepository;
import com.fptpolytechnic.duan1.repository.OrderRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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


        OrderMonthRevenueResponse revenue = dashBoardRepository.getMonthlyRevenue();
        List<Order> orders = orderRepository.findAll(0, 10);
        Long newUserCount = dashBoardRepository.getUserByCurrentMonth();
        Long totalOrder = dashBoardRepository.getTotalOrderCount();
        Long lowStockCount = dashBoardRepository.getLowStockProductCount();

        System.out.println("New users this month: " + newUserCount);
        System.out.println("Total orders: " + totalOrder);
        System.out.println("Low stock count: " + lowStockCount);

        orders.forEach(order -> {
            System.out.println("Order ID: " + order.getId() + ", Total Amount: " + order.getTotalAmount() + ", Status: " + order.getOrderStatus());
        });

        req.setAttribute("orders", orders);
        req.setAttribute("revenue", revenue);
        req.setAttribute("newUserCount", newUserCount);
        req.setAttribute("totalOrder", totalOrder);
        req.setAttribute("lowStockCount", lowStockCount);
        req.getRequestDispatcher("/views/admin/dashboard.jsp").forward(req, resp);
    }
}
