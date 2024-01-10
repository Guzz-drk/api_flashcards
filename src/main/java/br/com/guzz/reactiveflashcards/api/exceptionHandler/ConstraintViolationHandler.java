package br.com.guzz.reactiveflashcards.api.exceptionHandler;

import static br.com.guzz.reactiveflashcards.domain.exception.BaseErrorMessage.GENERIC_BAD_REQUEST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.guzz.reactiveflashcards.api.controller.response.ErrorFieldResponse;
import br.com.guzz.reactiveflashcards.api.controller.response.ProblemResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ConstraintViolationHandler extends AbstractHandlerException<ConstraintViolationException> {

    public ConstraintViolationHandler(final ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, ConstraintViolationException ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, BAD_REQUEST);
            return GENERIC_BAD_REQUEST.getMessage();
        }).map(message -> buildError(BAD_REQUEST, message))
                .flatMap(response -> buildRequestErrorMessage(response, ex))
                .doFirst(() -> log.error("=== ConstraintViolationException: ", ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

    private Mono<ProblemResponse> buildRequestErrorMessage(final ProblemResponse response,
            final ConstraintViolationException ex) {
        return Flux.fromIterable(ex.getConstraintViolations())
                .map(constraint -> ErrorFieldResponse.builder()
                        .name(((PathImpl) constraint.getPropertyPath()).getLeafNode().toString())
                        .message(constraint.getMessage())
                        .build())
                .collectList()
                .map(problems -> response.toBuilder().fields(problems).build());
    }

}
