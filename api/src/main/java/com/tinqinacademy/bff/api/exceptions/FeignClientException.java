package com.tinqinacademy.bff.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class FeignClientException extends RuntimeException {
    private String message;
    private HttpStatus status;
}
