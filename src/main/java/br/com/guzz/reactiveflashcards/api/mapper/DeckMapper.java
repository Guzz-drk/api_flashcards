package br.com.guzz.reactiveflashcards.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.guzz.reactiveflashcards.api.controller.request.DeckRequest;
import br.com.guzz.reactiveflashcards.api.controller.response.DeckResponse;
import br.com.guzz.reactiveflashcards.domain.document.DeckDocument;

@Mapper(componentModel = "spring")
public interface DeckMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DeckDocument toDocument(final DeckRequest request);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DeckDocument toDocument(final DeckRequest request, final String id);

    DeckResponse toResponse(final DeckDocument document);
}
