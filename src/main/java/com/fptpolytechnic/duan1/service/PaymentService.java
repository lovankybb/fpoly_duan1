package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Order;

import java.util.Map;

public interface PaymentService {

    public String getUrl( Order order );

    public void ipnHandle(Map<String, String> params);

}
