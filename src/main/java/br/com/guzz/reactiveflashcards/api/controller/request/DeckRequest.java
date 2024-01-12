package br.com.guzz.reactiveflashcards.api.controller.request;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public record DeckRequest(
        @JsonProperty("name") @NotBlank @Size(min = 1, max = 255) String name,
        @JsonProperty("description") @NotBlank @Size(min = 1, max = 255) String description,
        @Valid @JsonProperty("cards") @NotNull @Size(min = 3) Set<CardRequest> cards) {

    @Builder(toBuilder = true)
    public DeckRequest {
    }
}