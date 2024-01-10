package br.com.guzz.reactiveflashcards.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping; 

import br.com.guzz.reactiveflashcards.api.controller.request.UserRequest;
import br.com.guzz.reactiveflashcards.api.controller.response.UserResponse;
import br.com.guzz.reactiveflashcards.domain.document.UserDocument;


@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDocument toDocument(final UserRequest userRequest);

    UserResponse toResponse(final UserDocument userDocument);
}
