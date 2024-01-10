package br.com.guzz.reactiveflashcards.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import br.com.guzz.reactiveflashcards.domain.document.UserDocument;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserDocument, String>{
    
}
