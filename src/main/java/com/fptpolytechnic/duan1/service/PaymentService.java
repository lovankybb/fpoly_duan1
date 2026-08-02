package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Order;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PaymentService {

    public String getUrl(HttpServletRequest request, long amount, String orderCode);

    public Map<String, String> ipnHandle(Map<String, String> params);

}
