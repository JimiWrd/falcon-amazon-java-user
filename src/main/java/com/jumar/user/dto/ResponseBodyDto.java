package com.jumar.user.dto;

import com.jumar.user.models.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ResponseBodyDto {
    private Boolean success;
    private UserDto response;
    private String message;
    private LocalDateTime timestamp;
}
