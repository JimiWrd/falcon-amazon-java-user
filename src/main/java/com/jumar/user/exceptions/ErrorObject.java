package com.jumar.user.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private LocalDateTime timestamp;

}
