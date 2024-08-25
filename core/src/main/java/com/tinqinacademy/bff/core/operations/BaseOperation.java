package com.tinqinacademy.bff.core.operations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.exceptions.FeignClientException;
import feign.FeignException;
import io.vavr.API;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public abstract class BaseOperation {
    //protected final Validator validator;
    protected final ConversionService conversionService;
    protected final ErrorMapper errorMapper;

//    public <T extends OperationRequest> void validate(T input) {
//        Set<ConstraintViolation<OperationRequest>> violations = validator.validate(input);
//
//        if (!violations.isEmpty()) {
//            List<Error> errors = new ArrayList<>();
//
//            violations.forEach(violation ->
//                    errors.add(Error.builder()
//                            .message(violation.getMessage())
//                            .field(violation.getPropertyPath().toString())
//                            .build())
//            );
//
//            throw new InvalidRequestException(errors);
//        }
//    }

    protected API.Match.Case<? extends Exception, ErrorWrapper> customCase(Throwable cause,
                                                                           HttpStatus status,
                                                                           Class<? extends Exception> exceptionClass) {
        return Case($(instanceOf(exceptionClass)), errorMapper.map(cause, status));
    }

    protected API.Match.Case<? extends Exception, ErrorWrapper> defaultCase(Throwable cause,
                                                                            HttpStatus status) {
        return Case($(), errorMapper.map(cause, status));
    }

//    protected API.Match.Case<InvalidRequestException, ErrorWrapper> validateCase(Throwable cause,
//                                                                                 HttpStatus status) {
//        List<Error> errors = cause instanceof InvalidRequestException ex ? ex.getErrors() : new ArrayList<>();
//
//        return Case($(instanceOf(InvalidRequestException.class)), () -> ErrorWrapper.builder()
//                .errors(errors)
//                .status(status)
//                .build());
//    }

    protected API.Match.Case<Exception, ErrorWrapper> feignCase(Throwable throwable) {
        FeignClientException feignClientException = makeFeignClientException((FeignException) throwable);

        return Case($(instanceOf(FeignException.class)), errorMapper.map(feignClientException, feignClientException.getStatus()));
    }

    private FeignClientException makeFeignClientException(FeignException throwable) {
        try {
            log.info("throwable: {}", throwable.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode errorBody = objectMapper.readTree(throwable.contentUTF8());
            String message = errorBody.get("errors").get(0).get("message").asText();
            String statusCode = errorBody.get("status").asText();

            HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
            return new FeignClientException(message, httpStatus);
        } catch (Exception e) {
            return new FeignClientException("unknown", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
