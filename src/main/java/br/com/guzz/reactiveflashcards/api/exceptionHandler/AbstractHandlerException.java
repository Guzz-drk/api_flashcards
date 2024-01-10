package br.com.guzz.reactiveflashcards.api.exceptionHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.time.OffsetDateTime;

import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.guzz.reactiveflashcards.api.controller.response.ProblemResponse;
import static lombok.AccessLevel.PROTECTED;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(access = PROTECTED)
public abstract class AbstractHandlerException<T extends Exception> {

    private final ObjectMapper objectMapper;

    abstract Mono<Void> handlerException(final ServerWebExchange exchange, final T ex);

    protected ProblemResponse buildError(final HttpStatus statusCode, final String errorDescription) {
        return ProblemResponse.builder()
                .statusCode(statusCode.value())
                .errorDescription(errorDescription)
                .timestamp(OffsetDateTime.now())
                .build();
    }

    protected void prepareExchange(final ServerWebExchange exchange, final HttpStatus statusCode) {
        exchange.getResponse().setStatusCode(statusCode);
        exchange.getResponse().getHeaders().setContentType(APPLICATION_JSON);
    }

    protected Mono<Void> writeResponse(final ServerWebExchange exchange, final ProblemResponse problemResponse) {
        return exchange.getResponse().writeWith(Mono.fromCallable(
                () -> new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(problemResponse))));
    }
}
