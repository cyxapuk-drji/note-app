package com.notes.app.dto.response;

import lombok.Data;

@Data
public class ErrorResponse {
    
    private String error;
    private String message;
    private int status;
}
