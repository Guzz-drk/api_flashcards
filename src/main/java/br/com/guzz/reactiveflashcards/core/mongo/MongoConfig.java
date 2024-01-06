package br.com.guzz.reactiveflashcards.core.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import br.com.guzz.reactiveflashcards.core.mongo.converter.DateToOffsetDateTimeConverter;
import br.com.guzz.reactiveflashcards.core.mongo.converter.OffsetDateTimeToDateConverter;

@Configuration
@EnableMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
public class MongoConfig {
    
    @Bean
    MongoCustomConversions mongoCustomConversions(){
        final List<Converter<?,?>> converters = new ArrayList<>();
        converters.add(new OffsetDateTimeToDateConverter());
        converters.add(new DateToOffsetDateTimeConverter());
        return new MongoCustomConversions(converters);
    }
}
