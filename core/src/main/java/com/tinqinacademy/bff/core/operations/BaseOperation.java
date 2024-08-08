package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.bff.api.base.OperationRequest;
import com.tinqinacademy.bff.api.errors.Error;
import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.exceptions.InvalidRequestException;
import io.vavr.API;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.vavr.API.$;
import static io.vavr.Predicates.instanceOf;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class BaseOperation {
    protected final Validator validator;
    protected final ConversionService conversionService;
    protected final ErrorMapper errorMapper;

    public <T extends OperationRequest> void validate(T input) {
        Set<ConstraintViolation<OperationRequest>> violations = validator.validate(input);

        if (!violations.isEmpty()) {
            List<Error> errors = new ArrayList<>();

            violations.forEach(violation ->
                    errors.add(Error.builder()
                            .message(violation.getMessage())
                            .field(violation.getPropertyPath().toString())
                            .build())
            );

            throw new InvalidRequestException(errors);
        }
    }

    protected API.Match.Case<? extends Exception, ErrorWrapper> customCase(Throwable cause,
                                                                           HttpStatus status,
                                                                           Class<? extends Exception> exceptionClass) {
        return API.Case($(instanceOf(exceptionClass)), errorMapper.map(cause, status));
    }

    protected API.Match.Case<? extends Exception, ErrorWrapper> defaultCase(Throwable cause,
                                                                            HttpStatus status) {
        return API.Case($(), errorMapper.map(cause, status));
    }

    protected API.Match.Case<InvalidRequestException, ErrorWrapper> validateCase(Throwable cause,
                                                                                 HttpStatus status) {
        List<Error> errors = cause instanceof InvalidRequestException ex ? ex.getErrors() : new ArrayList<>();

        return API.Case($(instanceOf(InvalidRequestException.class)), () -> ErrorWrapper.builder()
                .errors(errors)
                .status(status)
                .build());
    }
}
