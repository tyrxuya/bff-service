package com.tinqinacademy.bff.api.base;

import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import io.vavr.control.Either;

public interface OperationProcessor<S extends OperationRequest, T extends OperationResponse> {
    Either<ErrorWrapper, T> process(S input);
}
