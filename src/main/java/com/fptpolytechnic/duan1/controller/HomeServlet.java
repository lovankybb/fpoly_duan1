package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.dto.response.SimpleProdResponse;
import com.fptpolytechnic.duan1.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet({"", "/home"})
public class HomeServlet extends HttpServlet {

    private final ProductService productService;

    public HomeServlet() {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<SimpleProdResponse> products = productService.findNewestProducts();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
