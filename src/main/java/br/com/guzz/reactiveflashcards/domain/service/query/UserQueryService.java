package br.com.guzz.reactiveflashcards.domain.service.query;

import static br.com.guzz.reactiveflashcards.domain.exception.BaseErrorMessage.USER_NOT_FOUND;

import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.guzz.reactiveflashcards.domain.document.UserDocument;
import br.com.guzz.reactiveflashcards.domain.exception.NotFoundException;
import br.com.guzz.reactiveflashcards.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class UserQueryService {
    
    private final UserRepository userRepository;

    public Mono<UserDocument> findById(final String id){
        return userRepository.findById(id)
        .doFirst(() -> log.info("=== try to find user with id {}", id))
        .filter(Objects::nonNull)
        .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException(USER_NOT_FOUND.params(id).getMessage()))));
    }
}
