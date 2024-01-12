package br.com.guzz.reactiveflashcards.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.guzz.reactiveflashcards.api.controller.request.DeckRequest;
import br.com.guzz.reactiveflashcards.api.controller.response.DeckResponse;
import br.com.guzz.reactiveflashcards.api.mapper.DeckMapper;
import br.com.guzz.reactiveflashcards.domain.service.DeckService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;


@Validated
@RestController
@RequestMapping("decks")
@Slf4j
@AllArgsConstructor
public class DeckController {
    
    public final DeckService deckService;

    public final DeckMapper deckMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<DeckResponse> save(@Valid @RequestBody final DeckRequest request){
        return deckService.save(deckMapper.toDocument(request))
        .doFirst(() -> log.info("=== Saving a deck with follow data {}", request))
        .map(deckMapper::toResponse);
    }
}
