package br.com.guzz.reactiveflashcards.domain.exception;

public class NotFoundException extends ReactiveFlashcardsException{
    
    public NotFoundException(final String msg){
        super(msg);
    }
}
