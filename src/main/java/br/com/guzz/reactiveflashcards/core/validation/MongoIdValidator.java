package br.com.guzz.reactiveflashcards.core.validation;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoIdValidator implements ConstraintValidator<MongoId, String>{

    @Override
    public void initialize(final MongoId constraintAnnotation){
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.info("===  if {} is a valid mongoDB id. ", value);
        return StringUtils.isNotBlank(value) && ObjectId.isValid(value);
    }

}
