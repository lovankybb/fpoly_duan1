package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Order;

import java.util.Map;

public class VnpayService implements PaymentService{
    @Override
    public String getUrl(Order order) {
        return "";
    }

    @Override
    public void ipnHandle(Map<String, String> params) {

    }
}
