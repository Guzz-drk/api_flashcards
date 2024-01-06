package br.com.guzz.reactiveflashcards.core.mongo.converter;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class OffsetDateTimeToDateConverter implements Converter<Date, OffsetDateTime>{

    @Override
    @Nullable
    public OffsetDateTime convert(Date source) {
        return OffsetDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
    }
    
}
