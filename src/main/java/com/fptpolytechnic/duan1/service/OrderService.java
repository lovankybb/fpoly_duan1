package com.fptpolytechnic.duan1.service;


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
import com.fptpolytechnic.duan1.repository.OrderDetailRepository;
import com.fptpolytechnic.duan1.repository.OrderRepository;
import com.fptpolytechnic.duan1.repository.ProductVariantRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderService {


    private final OrderRepository orderRepository;
    private final ProductVariantService productVariantService;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserService userService;
    private PaymentService paymentService;
    private final ProductService productService;

    public OrderService() {
        this.productVariantRepository = new ProductVariantRepository();
        this.orderRepository = new OrderRepository();
        this.orderDetailRepository = new OrderDetailRepository();
        this.userService = new UserService();
        this.productService = new ProductService();
        this.productVariantService = new ProductVariantService();
    }

    public Order create(HttpServletRequest req) {

//        Customer information
        Order order = Order.builder()
                .customerName(req.getParameter("customerName"))
                .customerPhone(req.getParameter("customerPhone"))
                .customerAddress(req.getParameter("customerAddress"))
                .customerNote(req.getParameter("customerNote") == null ? "" : req.getParameter("customerNote"))
                .build();


//        Order code
        String orderCode = "ATELIER_ORD_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        order.setOrderCode(orderCode);

//        if this order belongs to a user
        Authentication auth = (Authentication) req.getAttribute("authentication");
        if (auth != null) {
            User user = userService.findByUsername(auth.getUsername());
            order.setUserId(user.getId());
        }

//        Payment
        String paymentMethodName = req.getParameter("paymentMethod");
        PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentMethodName);
        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus(PaymentStatus.PENDING);

//        Order status
        order.setOrderStatus(OrderStatus.PENDING);

//        Order time
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdateAt(LocalDateTime.now());

        return this.orderRepository.create(order);
    }

    public void persistOrderDetail(List<OrderDetail> orderDetails) throws SQLException {
        this.orderDetailRepository.insert(orderDetails);
        orderDetails.forEach(detail -> productVariantRepository.updateStock(detail.getVariantId(), detail.getQuantity(), true));
    }

    public Order getOrderById(Long orderId) throws SQLException {
        return this.orderRepository.returnOrder(orderId);
    }

    public Order getOrderByOrderCode(String orderCode) throws SQLException {
        return this.orderRepository.returnOrder(orderCode);
    }

    public Order updateTotalAmount(Long orderId, BigDecimal totalAmount) throws SQLException {
        return this.orderRepository.updateTotalAmount(orderId, totalAmount);
    }

    public List<OrderItemResponse> getOrderItems(Long orderId) throws SQLException {

        List<OrderItemResponse> orderItemResponses = new ArrayList<>();

        List<OrderDetail> orderDetails = this.orderDetailRepository.findByOrderId(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            SimpleProdResponse product = this.productService.findByProductVariantId(orderDetail.getVariantId());
            ProductVariantResponse variant = productVariantService.findById(orderDetail.getVariantId());
            orderItemResponses.add(OrderItemResponse.builder()
                    .id(orderDetail.getId())
                    .variantId(variant.getId())
                    .productName(product.getName())
                    .versionName(variant.getVersion().getName())
                    .colorName(variant.getColor().getName())
                    .imageUrl(product.getImage())
                    .quantity(orderDetail.getQuantity())
                    .price(orderDetail.getPrice().doubleValue())
                    .build());
        }
        return orderItemResponses;
    }


    public void cancelOrder(Long orderId, String cancelReason) throws SQLException {

        List<OrderDetail> orderDetails = this.orderDetailRepository.findByOrderId(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            this.productVariantRepository.updateStock(orderDetail.getVariantId(), orderDetail.getQuantity(), false);
        }
        this.orderRepository.updateCancelInfo(orderId, OrderStatus.CANCELLED, LocalDateTime.now(), cancelReason);
    }


    public void completeOrder(Long orderId) throws SQLException {
        this.orderRepository.updateCompleteInfo(orderId, OrderStatus.COMPLETED, PaymentStatus.PAID, LocalDateTime.now());
    }


    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) throws SQLException {
        switch (orderStatus) {
            case CANCELLED -> this.cancelOrder(orderId, "Cancelled by admin");
            case COMPLETED -> this.completeOrder(orderId);
            default -> this.orderRepository.updateOrderStatus(orderId, orderStatus);
        }

    }


    public void updatePaymentStatus(Long orderId, PaymentStatus paymentStatus) throws SQLException {
        this.orderRepository.updatePaymentStatus(orderId, paymentStatus);
    }

    public List<Order> findAll(int offset) {

        if (offset < 0) {
            offset = 0;
        }
        return this.orderRepository.findAll(offset, 20);
    }
}
