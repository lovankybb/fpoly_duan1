package com.fptpolytechnic.duan1.service;


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
import com.oracle.wls.shaded.org.apache.xpath.operations.Or;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderService {


    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserService userService;
    private PaymentService paymentService;

    public OrderService(){
        this.productVariantRepository = new ProductVariantRepository();
        this.orderRepository = new OrderRepository();
        this.orderDetailRepository = new OrderDetailRepository();
        this.userService = new UserService();
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
        if(auth != null){
            User user = userService.findByUsername(auth.getUsername());
            order.setUserId(user.getId());
        }

//        Payment
        String paymentMethodName = req.getParameter("paymentMethod");
        PaymentMethod paymentMethod =  PaymentMethod.valueOf(paymentMethodName);
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
    }


    public Order updateTotalAmount(Long orderId, BigDecimal totalAmount) throws SQLException {
        return this.orderRepository.updateTotalAmount(orderId, totalAmount);
    }
    public Order checkout(Long orderId) {


        return null;
    }
}
