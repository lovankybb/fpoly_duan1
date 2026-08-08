package com.fptpolytechnic.duan1.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSpendResponse {

    private int completedOrder;
    private double totalSpend;
}
