package br.com.guzz.reactiveflashcards.api.exceptionHandler;

import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.guzz.reactiveflashcards.domain.exception.NotFoundException;
import br.com.guzz.reactiveflashcards.domain.exception.ReactiveFlashcardsException;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
@AllArgsConstructor
public class ApiExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper;

    private final MessageSource messageSource;

    @Override
    public Mono<Void> handle(final ServerWebExchange exchange, final Throwable ex) {
        return Mono.error(ex)
                .onErrorResume(MethodNotAllowedException.class,
                        e -> new MethodNotAllowHandler(objectMapper).handlerException(exchange, e))
                .onErrorResume(NotFoundException.class,
                        e -> new NotFoundHandler(objectMapper).handlerException(exchange, e))
                .onErrorResume(ConstraintViolationException.class,
                        e -> new ConstraintViolationHandler(objectMapper).handlerException(exchange, e))
                .onErrorResume(WebExchangeBindException.class,
                        e -> new WebExchangeBindHandler(objectMapper, messageSource).handlerException(exchange, e))
                .onErrorResume(ResponseStatusException.class,
                        e -> new ResponseStatusHandler(objectMapper).handlerException(exchange, e))
                .onErrorResume(ReactiveFlashcardsException.class,
                        e -> new ReactiveFlashcardsHandler(objectMapper).handlerException(exchange, e))
                .onErrorResume(Exception.class, e -> new ExceptionHandler(objectMapper).handlerException(exchange, e))
                .onErrorResume(JsonProcessingException.class,
                        e -> new JsonProcessingHandler(objectMapper).handlerException(exchange, e))
                .then();
    }
}
