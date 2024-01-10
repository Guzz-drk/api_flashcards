package br.com.guzz.reactiveflashcards.domain.service;

import org.springframework.stereotype.Service;

import br.com.guzz.reactiveflashcards.domain.document.UserDocument;
import br.com.guzz.reactiveflashcards.domain.repository.UserRepository;
import br.com.guzz.reactiveflashcards.domain.service.query.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserQueryService userQueryService;

    public Mono<UserDocument> save(final UserDocument userDocument) {
        return userRepository.save(userDocument)
                .doFirst(() -> log.info("=== try to save a follow user {} ", userDocument));
    }

    public Mono<UserDocument> update(final UserDocument document) {
        return userQueryService.findById(document.id())
                .map(user -> document.toBuilder()
                        .createdAt(user.createdAt())
                        .updatedAt(user.updatedAt())
                        .build())
                .flatMap(userRepository::save)
                .doFirst(() -> log.info("=== Try to update a user with follow info {}", document));
    }

    public Mono<Void> delete(final String id){
        return userQueryService.findById(id)
        .flatMap(userRepository::delete)
        .doFirst(() -> log.info("=== Try to delete a user with follow id {}", id));
    }
}
