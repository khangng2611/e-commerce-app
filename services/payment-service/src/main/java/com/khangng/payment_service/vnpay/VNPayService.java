package com.khangng.payment_service.vnpay;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.khangng.payment_service.config.VNPayConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RequiredArgsConstructor
@Service
public class VNPayService {
    private final VNPayConfig vnPayConfig;
    
    public String createPaymentUrl(VNPayRequest vnPayRequest) throws UnsupportedEncodingException {
        Map<String, String> vnPayParams = vnPayConfig.getVNPayConfig();
        vnPayParams.put("vnp_TxnRef", vnPayRequest.orderId());
        vnPayParams.put("vnp_OrderInfo", "Thanh toan don hang:" + vnPayRequest.orderId());
        vnPayParams.put("vnp_IpAddr", vnPayRequest.ipAddress());
        
        long amount = (long) (vnPayRequest.amount()*100);
        vnPayParams.put("vnp_Amount", String.valueOf(amount));
        
        List<String> fieldList = new ArrayList<>(vnPayParams.keySet());
        Collections.sort(fieldList);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldList.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = vnPayParams.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        
        String vnp_SecureHash = VNPayUtils.hmacSHA512(vnPayConfig.getHashSecret(), hashData.toString());
        return vnPayConfig.getUrl() + "?" + query.append("&vnp_SecureHash=").append(vnp_SecureHash).toString();
    }

}
