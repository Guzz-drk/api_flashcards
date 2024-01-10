package br.com.guzz.reactiveflashcards.api.exceptionHandler;

import org.springframework.core.annotation.Order;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.guzz.reactiveflashcards.domain.exception.NotFoundException;
import br.com.guzz.reactiveflashcards.domain.exception.ReactiveFlashcardsException;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
@AllArgsConstructor
public class ApiExceptionHandler implements WebExceptionHandler {

        private final MethodNotAllowHandler methodNotAllowHandler;

        private final NotFoundHandler notFoundHandler;

        private final ConstraintViolationHandler constraintViolationHandler;

        private final WebExchangeBindHandler webExchangeBindHandler;

        private final ResponseStatusHandler responseStatusHandler;

        private final ReactiveFlashcardsHandler reactiveFlashcardsHandler;

        private final ExceptionHandler exceptionHandler;

        private final JsonProcessingHandler jsonProcessingHandler;

        @Override
        public Mono<Void> handle(final ServerWebExchange exchange, final Throwable ex) {
                return Mono.error(ex)
                        .onErrorResume(MethodNotAllowedException.class,
                                        e -> methodNotAllowHandler.handlerException(exchange, e))
                        .onErrorResume(NotFoundException.class,
                                        e -> notFoundHandler.handlerException(exchange, e))
                        .onErrorResume(ConstraintViolationException.class,
                                        e -> constraintViolationHandler.handlerException(exchange, e))
                        .onErrorResume(WebExchangeBindException.class,
                                        e -> webExchangeBindHandler.handlerException(exchange, e))
                        .onErrorResume(ResponseStatusException.class,
                                        e -> responseStatusHandler.handlerException(exchange, e))
                        .onErrorResume(ReactiveFlashcardsException.class,
                                        e -> reactiveFlashcardsHandler.handlerException(exchange, e))
                        .onErrorResume(Exception.class,
                                        e -> exceptionHandler.handlerException(exchange, e))
                        .onErrorResume(JsonProcessingException.class,
                                        e -> jsonProcessingHandler.handlerException(exchange, e))
                        .then();
        }
}
