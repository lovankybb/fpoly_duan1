package com.fptpolytechnic.duan1.service.impl;

import com.fptpolytechnic.duan1.enums.PaymentStatus;
import com.fptpolytechnic.duan1.model.Order;
import com.fptpolytechnic.duan1.repository.OrderRepository;
import com.fptpolytechnic.duan1.service.PaymentService;
import com.fptpolytechnic.duan1.utils.VNPayConfig;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class VNPayService implements PaymentService {


    private final VNPayConfig vnPayConfig;
    private final OrderRepository orderRepository = new OrderRepository();

    public VNPayService() {
        this.vnPayConfig = new VNPayConfig();
    }

    public String getUrl(HttpServletRequest request, long amount, String orderCode) {

        long finalAmount = amount * 100;
        String vnp_TxnRef = orderCode;
        String vnp_IpAddr = request.getRemoteAddr();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnPayConfig.getVnpTmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(finalAmount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getVnpReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        System.out.println("Create Date: " + formatter.format(cld.getTime()));
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return vnPayConfig.getVnpPayUrl() + "?" + queryUrl;
    }


    public Map<String, String> ipnHandle(Map<String, String> params) {
        Map<String, String> response = new HashMap<>();
        try {
            Map<String, String> fields = new HashMap<>(params);
            String vnp_SecureHash = fields.remove("vnp_SecureHash");
            fields.remove("vnp_SecureHashType");

            String signValue = vnPayConfig.hashAllFields(fields);

            if (!signValue.equals(vnp_SecureHash)) {
                response.put("RspCode", "97");
                response.put("Message", "Invalid Checksum");
                return response;
            }

            String orderCode = params.get("vnp_TxnRef");
            Order order = orderRepository.returnOrder(orderCode);
            if (order == null) {
                response.put("RspCode", "01");
                response.put("Message", "Order not found");
                return response;
            }

            long vnpAmount = Long.parseLong(params.get("vnp_Amount"));
            long orderAmount = order.getTotalAmount().multiply(BigDecimal.valueOf(100)).longValue();
            if (vnpAmount != orderAmount) {
                response.put("RspCode", "04");
                response.put("Message", "Invalid Amount");
                return response;
            }

            if (order.getPaymentStatus() == PaymentStatus.PAID) {
                response.put("RspCode", "02");
                response.put("Message", "Order already confirmed");
                return response;
            }

            if ("00".equals(params.get("vnp_ResponseCode"))) {
                orderRepository.updatePaymentStatus(order.getId(), PaymentStatus.PAID);
            } else {
                orderRepository.updatePaymentStatus(order.getId(), PaymentStatus.UNPAID);
            }

            response.put("RspCode", "00");
            response.put("Message", "Confirm Success");
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.put("RspCode", "99");
            response.put("Message", "Unknown error");
            return response;
        }
    }
}
