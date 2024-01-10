package br.com.guzz.reactiveflashcards.api.exceptionHandler;

import static br.com.guzz.reactiveflashcards.domain.exception.BaseErrorMessage.GENERIC_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class ResponseStatusHandler extends AbstractHandlerException<ResponseStatusException> {

    public ResponseStatusHandler(final ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, ResponseStatusException ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, BAD_REQUEST);
            return GENERIC_NOT_FOUND.getMessage();
        }).map(message -> buildError(BAD_REQUEST, message))
                .doFirst(() -> log.error("=== ResponseStatusException: ", ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

}
