package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.api.base.OperationResponse;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public abstract class BaseController {
    public ResponseEntity<?> getResponseEntity(Either<ErrorWrapper, ? extends OperationResponse> result, HttpStatus status) {
        return result.fold(
                errorOutput -> new ResponseEntity<>(errorOutput, errorOutput.getStatus()),
                output -> new ResponseEntity<>(output, status)
        );
    }
}
