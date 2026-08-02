package com.fptpolytechnic.duan1.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderMonthRevenueResponse{
    private double monthRevenue;
    private long orderCount;
}
