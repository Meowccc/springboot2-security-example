package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author meow
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {

    private boolean success;

    private String traceId;

    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public static <T> ResponseDTO<T> ok() {
        return ResponseDTO.<T>builder()
                .success(Boolean.TRUE)
                .build();
    }

    public static <T> ResponseDTO<T> ok(T data) {
        return ResponseDTO.<T>builder()
                .success(Boolean.TRUE)
                .data(data)
                .build();
    }

    public static <T> ResponseDTO<T> error(final String code) {
        return ResponseDTO.<T>builder()
                .success(Boolean.FALSE)
                .code(code)
                .build();
    }
}
