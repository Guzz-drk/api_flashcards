package br.com.guzz.reactiveflashcards.core.mongo.converter;

import java.time.OffsetDateTime;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class DateToOffsetDateTimeConverter implements Converter<OffsetDateTime, Date>{

    @Override
    @Nullable
    public Date convert(OffsetDateTime source) {
        return Date.from(source.toInstant());
    }

}
