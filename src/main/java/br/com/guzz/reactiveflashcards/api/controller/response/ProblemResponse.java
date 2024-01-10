package br.com.guzz.reactiveflashcards.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(NON_NULL)
public record ProblemResponse(
        Integer statusCode,
        OffsetDateTime timestamp,
        String errorDescription,
        List<ErrorFieldResponse> fields) {

    @Builder(toBuilder = true)
    public ProblemResponse {
    }
}
