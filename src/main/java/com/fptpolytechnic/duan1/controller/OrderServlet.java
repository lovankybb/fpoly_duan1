package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.dto.response.CheckoutItem;
import com.fptpolytechnic.duan1.dto.response.ProductVariantResponse;
import com.fptpolytechnic.duan1.dto.response.SimpleProdResponse;
import com.fptpolytechnic.duan1.model.Authentication;
import com.fptpolytechnic.duan1.model.ProductVariant;
import com.fptpolytechnic.duan1.model.User;
import com.fptpolytechnic.duan1.repository.OrderRepository;
import com.fptpolytechnic.duan1.repository.ProductRepository;
import com.fptpolytechnic.duan1.repository.UserRepository;
import com.fptpolytechnic.duan1.service.OrderService;
import com.fptpolytechnic.duan1.service.ProductService;
import com.fptpolytechnic.duan1.service.ProductVariantService;
import com.fptpolytechnic.duan1.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet({"/checkout", "/admin/orders"})
public class OrderServlet extends HttpServlet {

    private final UserService userService;
    private final OrderService orderService;
    private final ProductService productService;
    private final ProductVariantService productVariantService;


    public OrderServlet() {
        userService = new UserService();
        orderService = new OrderService();
        productService = new ProductService();
        productVariantService = new ProductVariantService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        switch (path) {

            case "/checkout":
                responseCheckout(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        switch (path) {

            case "/checkout":
                responseCheckout(req, resp);
                break;
        }
    }

    private void responseCheckout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getParameterMap().forEach((key, value) -> {
            System.out.println(key + ": " + Arrays.toString(value));
        });

        String productId = req.getParameter("productId");
        if (productId == null || productId.trim().isEmpty()) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }

        String variantId = req.getParameter("variantId");
        if (variantId == null || variantId.trim().isEmpty()) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }

        String quantity = req.getParameter("quantity");
        if (quantity == null || quantity.trim().isEmpty()) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }

        Authentication auth = (Authentication) req.getAttribute("authentication");

        if (auth != null) {
            User user = userService.findByUsername(auth.getUsername());
            if (user != null) {
                req.setAttribute("customerName", user.getUsername());
                req.setAttribute("customerPhone", user.getPhone() != null ? user.getPhone() : "");
                req.setAttribute("customerAddress", user.getAddress() != null ? user.getAddress() : "");
            }
        }


        SimpleProdResponse product = this.productService.findById(Long.valueOf(productId));
        ProductVariantResponse variant = this.productVariantService.findById(Long.valueOf(variantId));

        List<CheckoutItem> checkoutItems = new ArrayList<>();

        checkoutItems.add(CheckoutItem.builder()
                        .productName(product.getName())
                        .colorName(variant.getColor().getName())
                        .versionName(variant.getVersion().getName())
                        .imageUrl(product.getImage())
                .build());


        req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
    }
}
