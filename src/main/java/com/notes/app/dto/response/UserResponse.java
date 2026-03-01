package com.notes.app.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    
    private Long id;
    private String username;
    private LocalDateTime createdAt;
}
