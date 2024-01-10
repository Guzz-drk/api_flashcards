package br.com.guzz.reactiveflashcards.api.exceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.guzz.reactiveflashcards.domain.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class NotFoundHandler extends AbstractHandlerException<NotFoundException> {

    public NotFoundHandler(final ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, NotFoundException ex) {
        return Mono.fromCallable(() -> {
            prepareExchange(exchange, NOT_FOUND);
            return ex.getMessage();
        }).map(message -> buildError(NOT_FOUND, message))
                .doFirst(() -> log.error("=== NotFoundException: ", ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

}
