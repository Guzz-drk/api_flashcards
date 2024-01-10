package br.com.guzz.reactiveflashcards.api.exceptionHandler;

import static br.com.guzz.reactiveflashcards.domain.exception.BaseErrorMessage.GENERIC_METHOD_NOT_ALLOW;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class JsonProcessingHandler extends AbstractHandlerException<JsonProcessingException> {
    public JsonProcessingHandler(final ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, JsonProcessingException ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, INTERNAL_SERVER_ERROR);
            return GENERIC_METHOD_NOT_ALLOW.getMessage();
        }).map(message -> buildError(INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.error("=== JsonProcessingException: Failed to map exception for the request {} ",
                        exchange.getRequest().getPath().value(), ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

}
