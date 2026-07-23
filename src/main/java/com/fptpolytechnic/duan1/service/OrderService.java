package com.fptpolytechnic.duan1.service;


import com.fptpolytechnic.duan1.model.Authentication;
import com.fptpolytechnic.duan1.model.Order;
import com.fptpolytechnic.duan1.repository.OrderDetailRepository;
import com.fptpolytechnic.duan1.repository.OrderRepository;
import com.fptpolytechnic.duan1.repository.ProductVariantRepository;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public class OrderService {


    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;
    private final ProductVariantRepository productVariantRepository;

    private PaymentService paymentService;

    public OrderService(){
        this.productVariantRepository = new ProductVariantRepository();
        this.orderRepository = new OrderRepository();
        this.orderDetailRepository = new OrderDetailRepository();
    }

    public Order create(Order order, HttpServletRequest req) {


        String orderCode = "ATELIER_ORD_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        order.setOrderCode(orderCode);

        Authentication auth = (Authentication) req.getAttribute("authentication");

        if(auth != null){
            order.setUserId(auth.getUsername());
        }


        return this.orderRepository.create(order);
    }

    public Order checkout(Long orderId) {


        return null;
    }
}
