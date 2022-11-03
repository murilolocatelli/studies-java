package com.study.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class EmailDto {

    private final String subject;

    private final String body;
    
}
