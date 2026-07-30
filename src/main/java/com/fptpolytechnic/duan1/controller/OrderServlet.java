package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.dto.CheckoutFormDTO;
import com.fptpolytechnic.duan1.dto.response.CheckoutItem;
import com.fptpolytechnic.duan1.dto.response.ProductVariantResponse;
import com.fptpolytechnic.duan1.dto.response.SimpleProdResponse;
import com.fptpolytechnic.duan1.model.Authentication;
import com.fptpolytechnic.duan1.model.Order;
import com.fptpolytechnic.duan1.model.OrderDetail;
import com.fptpolytechnic.duan1.model.User;
import com.fptpolytechnic.duan1.service.OrderService;
import com.fptpolytechnic.duan1.service.ProductService;
import com.fptpolytechnic.duan1.service.ProductVariantService;
import com.fptpolytechnic.duan1.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

@WebServlet({
        "/checkout",
        "/place-order",
        "/admin/orders",
        "/orders-success",
        "/orders-failed"
})
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

            case "/orders-success":
                this.responseOrderSuccess(req, resp);
                break;
            case "/orders-failed":
                this.responseOrderFailed(req, resp);
                break;

            default:
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
            case "/place-order":
                handleOrder(req, resp);
                break;
        }
    }

    private void responseOrderFailed(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/views/order-failed.jsp").forward(req, resp);
    }



    private void responseOrderSuccess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String orderCode = req.getParameter("orderCode");

        if (orderCode == null || orderCode.trim().isEmpty()) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }

        req.setAttribute("orderCode", orderCode);

        Order order = null;
        try {
            order = orderService.getOrderByOrderCode(orderCode);
        } catch (SQLException e) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }

        if (order == null) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }

        req.setAttribute("paymentMethod", order.getPaymentMethod().name());
        req.setAttribute("totalAmount", order.getTotalAmount().doubleValue());
        req.getRequestDispatcher("/views/order-success.jsp").forward(req, resp);
    }

    private void responseCheckout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String checkoutType = req.getParameter("checkoutType");
        if (checkoutType == null || checkoutType.trim().isEmpty()) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }

        if (checkoutType.equals("BUY_NOW")) {
            this.responseByNowCheckoutForm(req, resp);
        } else {
            this.responseDefaultCheckout(req, resp);
        }
    }

    private void handleOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        Object checkoutTypeObj = session.getAttribute("CHECKOUT_TYPE");

        System.out.println("*******: Checkout_Type: " + checkoutTypeObj);
        String checkoutType = (String) checkoutTypeObj;

        if ("BUY_NOW".equals(checkoutType)) {
            this.handleBuyNowOrder(req, resp, session);
        } else {
//            Handle default ch
        }

    }

    private void handleBuyNowOrder(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException {


        System.out.println("INFO: Processing order......");
        List<CheckoutItem> checkoutItems = (List<CheckoutItem>) session.getAttribute("CHECKOUT_ITEMS");

        CheckoutFormDTO form = CheckoutFormDTO.builder()
                .customerName(req.getParameter("customerName"))
                .customerPhone(req.getParameter("customerPhone"))
                .customerAddress(req.getParameter("customerAddress"))
                .paymentMethod(req.getParameter("paymentMethod"))
                .build();

        StringBuilder url = new StringBuilder(req.getContextPath()).append("/checkout?checkoutType=BUY_NOW");

        Map<String, String> errors = this.validateCheckoutForm(form);

        if (!errors.isEmpty()) {

            System.out.println("ERROR: Validation Failed");
            errors.forEach((key, value) -> {
                url.append("&").append(key).append("=").append(value);
            });
            resp.sendRedirect(url.toString());
        } else {
            System.out.println("INFO: Validation Successful!");

            Order order = orderService.create(req);

            System.out.println("INFO: Order created successfully with order_code: " + order.getOrderCode());
            if (!Objects.isNull(order)) {

                List<OrderDetail> orderDetails = new ArrayList<>();
                BigDecimal totalAmount = BigDecimal.ZERO;
                for (CheckoutItem item : checkoutItems) {
                    orderDetails.add(OrderDetail.builder()
                            .orderId(order.getId())
                            .price(BigDecimal.valueOf(item.getPrice()))
                            .quantity(item.getQuantity())
                            .variantId(item.getVariantId())
                            .build());

                    totalAmount = totalAmount.add(BigDecimal.valueOf(item.getQuantity()).multiply(BigDecimal.valueOf(item.getPrice())));
                }
                try {
                    orderService.persistOrderDetail(orderDetails);
                    orderService.updateTotalAmount(order.getId(), totalAmount);
                    System.out.println("INFO: Persist order detail and updated total amount successfully! ");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            resp.sendRedirect(req.getContextPath() + "/orders-success" + "?orderCode=" + order.getOrderCode());

        }
    }

    private void responseByNowCheckoutForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String variantId = req.getParameter("productVariantId");
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


        SimpleProdResponse product = this.productService.findByProductVariantId(Long.valueOf(variantId));
        ProductVariantResponse variant = this.productVariantService.findById(Long.valueOf(variantId));

        List<CheckoutItem> checkoutItems = new ArrayList<>();

        checkoutItems.add(CheckoutItem.builder()
                .variantId(variant.getId())
                .productName(product.getName())
                .colorName(variant.getColor().getName())
                .versionName(variant.getVersion().getName())
                .imageUrl(product.getImage())
                .price(variant.getPrice().doubleValue())
                .quantity(Integer.parseInt(quantity))
                .build());

        BigDecimal subTotal = BigDecimal.ZERO;

        for (CheckoutItem item : checkoutItems) {
            subTotal = subTotal.add(BigDecimal.valueOf(item.getPrice()).multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        HttpSession session = req.getSession();
        session.setAttribute("CHECKOUT_ITEMS", checkoutItems);
        session.setAttribute("CHECKOUT_TYPE", "BUY_NOW");
        session.setMaxInactiveInterval(900);

        req.setAttribute("checkoutItems", checkoutItems);
        req.setAttribute("subTotal", subTotal);

        req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
    }

    private void responseDefaultCheckout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }


    private Map<String, String> validateCheckoutForm(CheckoutFormDTO form) throws ServletException, IOException {

        Map<String, String> errors = new HashMap<>();

        if (form.getCustomerName() == null || form.getCustomerName().trim().isEmpty()) {
            errors.put("customerNameError", "true");
        }
        if (form.getCustomerPhone() == null || form.getCustomerPhone().trim().isEmpty()) {
            errors.put("customerPhoneError", "true");
        }
        if (form.getCustomerAddress() == null || form.getCustomerAddress().trim().isEmpty()) {
            errors.put("customerAddressError", "true");
        }

        if (form.getPaymentMethod() == null || form.getPaymentMethod().trim().isEmpty()) {
            errors.put("paymentMethodError", "true");
        }
        return errors;
    }
}
