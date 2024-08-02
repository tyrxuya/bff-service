package com.tinqinacademy.bff.api.base;

import com.tinqinacademy.bff.api.errors.ErrorOutput;
import io.vavr.control.Either;

public interface OperationProcessor<S extends OperationInput, T extends OperationOutput> {
    Either<ErrorOutput, T> process(S input);
}
