package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.service.impl.VNPayService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/payment/ipn-handle/vnpay")
public class PaymentServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Processing ipn-handle vnpay");

        Map<String, String[]> params = req.getParameterMap();
        Map<String, String> paramsMap = new HashMap<>();

        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            if (values != null && values.length > 0) {
                paramsMap.put(key, values[0]);
            }
        }
        VNPayService paymentService = new VNPayService();
        Map<String, String> result = paymentService.ipnHandle(paramsMap);


//        config to response result
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);

        String jsonResponse = String.format("{\"RspCode\":\"%s\",\"Message\":\"%s\"}",
                result.get("RspCode"), result.get("Message"));


        System.out.println("Response to VNPay: " + jsonResponse);
        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
        out.flush();
    }
}
