package com.evaluacion.utils;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.evaluacion.dto.ResponseDTO;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {

     @Test
    void buildErrorResponse_ShouldReturnProperResponseEntity() {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Resource Not Found";
        String message = "Invalid Data";

        ResponseEntity<Map<String, Object>> response = Utils.buildErrorResponse(status, error, message);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Map<String, Object> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.get("status")).isEqualTo(400);
        assertThat(body.get("error")).isEqualTo("Resource Not Found");
        assertThat(body.get("message")).isEqualTo("Invalid Data");
        assertThat(body.get("timestamp")).isInstanceOfAny(java.time.LocalDateTime.class);
    }

    @Test
    void buildResponse_ShouldReturnResponseDTOWrappedInResponseEntity() {
        Object data = "employee-data-test";
        String message = "success";
        HttpStatus status = HttpStatus.OK;

        ResponseEntity<ResponseDTO> response = Utils.buildResponse(data, message, status);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseDTO body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getMessage()).isEqualTo("success");
        assertThat(body.getResponseData()).isEqualTo("employee-data-test");
        assertThat(body.getSuccess()).isTrue();
    }
}
