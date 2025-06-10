package com.evaluacion.utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.evaluacion.dto.ResponseDTO;

public final class Utils {

    public static ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String error, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }

    
    public static ResponseEntity<ResponseDTO> buildResponse(Object data, String message, HttpStatus status) {
        ResponseDTO resp = ResponseDTO.builder()
                .responseData(data)
                .message(message)
                .success(true)
                .build();
        return new ResponseEntity<>(resp, status);
    }
}
