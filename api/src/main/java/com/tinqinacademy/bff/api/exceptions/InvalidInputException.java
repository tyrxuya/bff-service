package com.tinqinacademy.bff.api.exceptions;

import com.tinqinacademy.bff.api.errors.Error;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class InvalidInputException extends RuntimeException {
    private final List<Error> errors;
}
