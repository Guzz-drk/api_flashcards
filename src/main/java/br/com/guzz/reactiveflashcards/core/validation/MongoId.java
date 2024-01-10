package br.com.guzz.reactiveflashcards.core.validation;

import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;

@Target({TYPE, METHOD, FIELD, PARAMETER, TYPE_USE, ANNOTATION_TYPE, CONSTRUCTOR})
@Retention(RUNTIME)
@Constraint(validatedBy = {MongoIdValidator.class})
public @interface MongoId {
    
    String message() default "{br.com.guzz.reactiveflashcards.MongoId.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
