package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.dto.CheckoutFormDTO;
import com.fptpolytechnic.duan1.dto.response.OrderItemResponse;
import com.fptpolytechnic.duan1.dto.response.ProductVariantResponse;
import com.fptpolytechnic.duan1.dto.response.SimpleProdResponse;
import com.fptpolytechnic.duan1.enums.OrderStatus;
import com.fptpolytechnic.duan1.enums.PaymentMethod;
import com.fptpolytechnic.duan1.enums.PaymentStatus;
import com.fptpolytechnic.duan1.model.Authentication;
import com.fptpolytechnic.duan1.model.Order;
import com.fptpolytechnic.duan1.model.OrderDetail;
import com.fptpolytechnic.duan1.model.User;
import com.fptpolytechnic.duan1.service.*;
import com.fptpolytechnic.duan1.service.impl.VNPayService;
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
        "/admin/orders",
        "/admin/order/detail",
        "/admin/order/cancel",
        "/admin/order/update-payment",
        "/admin/order/update-status",
        "/checkout",
        "/place-order",
        "/orders-success",
        "/orders-failed",
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
                try {
                    this.responseOrderSuccess(req, resp);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/orders-failed":
                this.responseOrderFailed(req, resp);
                break;
            case "/admin/orders":
                this.responseOderManagement(req, resp);
                break;
            case "/admin/order/detail":
                try {
                    this.responseOrderDetailManagement(req, resp);
                } catch (SQLException e) {
                    resp.sendRedirect("/error?code=UNCATEGORIZED");
                }
                break;

            case "/admin/order/cancel":
                this.handleCancelOrder(req, resp);
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
            case "/admin/order/update-payment":
                this.handleUpdatePaymentStatus(req, resp);
                break;
            case "/admin/order/update-status":
                this.handleUpdateOrderStatus(req, resp);
                break;
        }
    }


    private void handleCancelOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");

        String cancelReason = req.getParameter("reason");
        if (orderId == null || orderId.trim().isEmpty()) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }

        if (cancelReason == null || cancelReason.trim().isEmpty()) {
            cancelReason = "";
        }

        try {
            orderService.cancelOrder(Long.parseLong(orderId), cancelReason);
            resp.sendRedirect("/admin/orders");
        } catch (SQLException e) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }

    }


    private void handleUpdatePaymentStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("id");
        String paymentStatus = req.getParameter("paymentStatus");

        if (orderId == null || orderId.trim().isEmpty()) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }

        if (paymentStatus == null || paymentStatus.trim().isEmpty()) {
            paymentStatus = PaymentStatus.UNPAID.name();
        }

        try {
            orderService.updatePaymentStatus(Long.parseLong(orderId), PaymentStatus.valueOf(paymentStatus));
            resp.sendRedirect("/admin/order/detail?id=" + orderId);
        } catch (SQLException e) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }
    }


    private void handleUpdateOrderStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("id");
        String orderStatus = req.getParameter("orderStatus");

        if (orderId == null || orderId.trim().isEmpty()) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }

        if (orderStatus == null || orderStatus.trim().isEmpty()) {
            orderStatus = "PENDING";
        }

        try {
            orderService.updateOrderStatus(Long.parseLong(orderId), OrderStatus.valueOf(orderStatus));
            resp.sendRedirect("/admin/order/detail?id=" + orderId);
        } catch (SQLException e) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }
    }


    private void responseOrderDetailManagement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String orderId = req.getParameter("id");

        if (orderId == null || orderId.trim().isEmpty()) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
        }

        Order order = this.orderService.getOrderById(Long.parseLong(orderId));

        List<OrderItemResponse> orderItems = this.orderService.getOrderItems(order.getId());
        req.setAttribute("order", order);
        req.setAttribute("orderItems", orderItems);

        req.getRequestDispatcher("/views/admin/order-detail.jsp").forward(req, resp);
    }


    private void responseOderManagement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String offset = req.getParameter("offset");

        if (offset == null || offset.trim().isEmpty()) {
            offset = "0";
        }

        List<Order> orders = this.orderService.findAll(Integer.parseInt(offset));

        req.setAttribute("orders", orders);
        req.setAttribute("offset", offset);

        req.getRequestDispatcher("/views/admin/order.jsp").forward(req, resp);
    }

    private void responseOrderFailed(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/views/order-failed.jsp").forward(req, resp);
    }


    private void responseOrderSuccess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, InterruptedException {

        String orderCode = req.getParameter("orderCode");

        if (orderCode == null || orderCode.trim().isEmpty()) {
            orderCode = req.getParameter("vnp_TxnRef");
        }

        req.setAttribute("orderCode", orderCode == null ? "OrderCode" : orderCode);

        Thread.sleep(2000); // Wait for 2 seconds to ensure the order is updated in the database
        Order order = null;
        try {
            order = orderService.getOrderByOrderCode(orderCode);
        } catch (SQLException e) {
            resp.sendRedirect("/error?code=UNCATEGORIZED");
            return;
        }


        System.out.println("INFO: Payment status: " + (order != null ? order.getPaymentStatus().name() : "UNKNOWN"));
        if (order != null) {
            req.setAttribute("paymentMethod", order.getPaymentMethod().name());
            req.setAttribute("totalAmount", order.getTotalAmount().doubleValue());
            req.setAttribute("paymentStatus", order.getPaymentStatus().name());
        }
        else {
            req.setAttribute("paymentMethod", "UNKNOWN");
            req.setAttribute("totalAmount", "UNKNOWN");
            req.setAttribute("paymentStatus", "UNKNOWN");
        }
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
        List<OrderItemResponse> checkoutItems = (List<OrderItemResponse>) session.getAttribute("CHECKOUT_ITEMS");

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
            errors.forEach((key, value) -> url.append("&").append(key).append("=").append(value));
            resp.sendRedirect(url.toString());
        } else {
            System.out.println("INFO: Validation Successful!");

            Order order = orderService.create(req);

            System.out.println("INFO: Order created successfully with order_code: " + order.getOrderCode());
            if (!Objects.isNull(order)) {

                List<OrderDetail> orderDetails = new ArrayList<>();
                BigDecimal totalAmount = BigDecimal.ZERO;
                for (OrderItemResponse item : checkoutItems) {
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

                    if (order.getPaymentMethod() == PaymentMethod.VNPAY && order.getPaymentStatus() != PaymentStatus.PAID) {
                        responseVnpayPayment( order.getId(), req, resp);
                    } else {
                        resp.sendRedirect(req.getContextPath() + "/orders-success" + "?orderCode=" + order.getOrderCode());
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void responseVnpayPayment(Long orderId, HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {

        PaymentService paymentService = new VNPayService();

        Order order = orderService.getOrderById(orderId);
        long totalAmount = order.getTotalAmount().longValue();
        String url = paymentService.getUrl(req, totalAmount, order.getOrderCode());

        System.out.println("Redirecting to VNPay URL: " + url);
        resp.sendRedirect(url);
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

        List<OrderItemResponse> checkoutItems = new ArrayList<>();

        if (Integer.parseInt(quantity) > variant.getStock()) {
            this.responseOrderFailed(req, resp);
        }
        checkoutItems.add(OrderItemResponse.builder()
                .variantId(variant.getId())
                .productName(product.getName())
                .colorName(variant.getColor().getName())
                .versionName(variant.getVersion().getName())
                .imageUrl(product.getImage())
                .price(variant.getPrice().doubleValue())
                .quantity(Integer.parseInt(quantity))
                .build());

        BigDecimal subTotal = BigDecimal.ZERO;

        for (OrderItemResponse item : checkoutItems) {
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
