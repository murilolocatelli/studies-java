package com.study.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {

    private String uuid;

    public String getReportPath() {
        return "target/" + this.uuid + "-report.txt";
    }

}
