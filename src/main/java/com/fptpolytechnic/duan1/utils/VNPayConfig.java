package com.fptpolytechnic.duan1.utils;

import lombok.Getter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Getter
public class VNPayConfig {

    private String vnpTmnCode;
    private String secretKey;
    private String vnpPayUrl ;
    private String vnpReturnUrl;
    private String vnpApiUrl;

    public VNPayConfig() {
       vnpTmnCode = AppConfig.getInstance().getProperty("app.vnpay.tmn-code");
       secretKey = AppConfig.getInstance().getProperty("app.vnpay.hash-secret");
       vnpPayUrl = AppConfig.getInstance().getProperty("app.vnpay.url");
       vnpReturnUrl = AppConfig.getInstance().getProperty("app.vnpay.return-url");
//       vnpApiUrl = AppConfig.getInstance().getProperty("app.vnpay.api-url");
    }

    public String hashAllFields(Map<String, String> fields) {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                try {
                    sb.append(java.net.URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    sb.append("=");
                    sb.append(java.net.URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        return hmacSHA512(secretKey, sb.toString());
    }

    public static String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }
}
