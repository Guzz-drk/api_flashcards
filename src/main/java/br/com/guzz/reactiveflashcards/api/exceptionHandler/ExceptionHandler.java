package br.com.guzz.reactiveflashcards.api.exceptionHandler;

import static br.com.guzz.reactiveflashcards.domain.exception.BaseErrorMessage.GENERIC_EXCEPTION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ExceptionHandler extends AbstractHandlerException<Exception> {
    public ExceptionHandler(final ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, Exception ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, INTERNAL_SERVER_ERROR);
            return GENERIC_EXCEPTION.getMessage();
        }).map(message -> buildError(INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.error("=== Exception: ", ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

}
