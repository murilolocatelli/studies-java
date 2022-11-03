package com.study.ecommerce.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class OrderDto {

    private final String orderId;

    private final BigDecimal amount;

    private final String email;

}
