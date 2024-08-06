package com.example.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private List<ErrorDTO> error;

    @Data
    @Builder
    public static class ErrorDTO {

        @JsonProperty("codigo")
        private Integer code;

        private String detail;

        private LocalDateTime timestamp;

    }

}
