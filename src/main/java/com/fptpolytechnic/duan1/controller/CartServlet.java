package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.dto.response.CartItemResponse;
import com.fptpolytechnic.duan1.model.Authentication;
import com.fptpolytechnic.duan1.model.User;
import com.fptpolytechnic.duan1.service.CartService;
import com.fptpolytechnic.duan1.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet({
        "/cart",
        "/add-to-cart",
        "/update-cart",
        "/remove-from-cart"
})
public class CartServlet extends HttpServlet {

    private final CartService cartService;
    private final UserService userService;

    public CartServlet() {
        this.cartService = new CartService();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();
        if ("/cart".equals(path)) {
            this.responseCartPage(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();
        switch (path) {
            case "/add-to-cart":
                this.handleAddToCart(req, resp);
                break;
            case "/update-cart":
                this.handleUpdateCart(req, resp);
                break;
            case "/remove-from-cart":
                this.handleRemoveFromCart(req, resp);
                break;
            default:
                break;
        }
    }

    private String getCurrentUserId(HttpServletRequest req) {

        Authentication auth = (Authentication) req.getAttribute("authentication");
        if (auth == null) {
            return null;
        }
        User user = userService.findByUsername(auth.getUsername());
        return user != null ? user.getId() : null;
    }

    private void responseCartPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userId = this.getCurrentUserId(req);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/sign-in");
            return;
        }

        List<CartItemResponse> cartItems = cartService.getCartItems(userId);
        req.setAttribute("cartItems", cartItems);

        req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
    }

    private void handleAddToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String userId = this.getCurrentUserId(req);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/sign-in");
            return;
        }

        String variantId = req.getParameter("productVariantId");
        String quantity = req.getParameter("quantity");

        if (variantId == null || variantId.trim().isEmpty()
                || quantity == null || quantity.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }

        try {
            cartService.addToCart(userId, Long.parseLong(variantId), Integer.parseInt(quantity));
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private void handleUpdateCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String userId = this.getCurrentUserId(req);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/sign-in");
            return;
        }

        String variantId = req.getParameter("variantId");
        String quantity = req.getParameter("quantity");

        if (variantId == null || variantId.trim().isEmpty()
                || quantity == null || quantity.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }

        try {
            cartService.updateQuantity(userId, Long.parseLong(variantId), Integer.parseInt(quantity));
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private void handleRemoveFromCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String userId = this.getCurrentUserId(req);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/sign-in");
            return;
        }

        String variantId = req.getParameter("variantId");
        if (variantId == null || variantId.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }

        try {
            cartService.removeItem(userId, Long.parseLong(variantId));
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}