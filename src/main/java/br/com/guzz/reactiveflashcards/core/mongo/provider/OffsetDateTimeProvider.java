package br.com.guzz.reactiveflashcards.core.mongo.provider;

import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

import static java.time.ZoneOffset.UTC;

@Component("dateTimeProvider")
public class OffsetDateTimeProvider implements DateTimeProvider{

    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(OffsetDateTime.now(UTC));
    }
    
}
