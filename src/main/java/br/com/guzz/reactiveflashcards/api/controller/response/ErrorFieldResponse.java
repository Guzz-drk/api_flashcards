package br.com.guzz.reactiveflashcards.api.controller.response;

import lombok.Builder;

public record ErrorFieldResponse(
        String name,
        String message) {

    @Builder(toBuilder = true)
    public ErrorFieldResponse {
    }
}
