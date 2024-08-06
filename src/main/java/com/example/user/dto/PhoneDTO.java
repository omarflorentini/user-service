package com.example.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {

    private Long number;

    @JsonProperty("citycode")
    private Integer cityCode;

    @JsonProperty("countrycode")
    private String countryCode;

}
