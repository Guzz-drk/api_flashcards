package br.com.guzz.reactiveflashcards.domain.service;

import org.springframework.stereotype.Service;

import br.com.guzz.reactiveflashcards.domain.document.UserDocument;
import br.com.guzz.reactiveflashcards.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {
    
    private final UserRepository userRepository;

    public Mono<UserDocument> save(final UserDocument userDocument){
        return userRepository.save(userDocument)
        .doFirst(() -> log.info("=== try to save a follow documente {} ", userDocument));
    }
}
