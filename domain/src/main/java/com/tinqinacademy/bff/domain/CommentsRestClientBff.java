package com.tinqinacademy.bff.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentsRestClientBff {
    private final ApplicationContext applicationContext;

    @Value("${comments.client.url}")
    private String COMMENTS_URL;

    @Bean
    public CommentsRestExport getCommentsRestClient() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return Feign.builder()
                .encoder(new JacksonEncoder(applicationContext.getBean(ObjectMapper.class)))
                .decoder(new JacksonDecoder(applicationContext.getBean(ObjectMapper.class)))
                .target(CommentsRestExport.class, COMMENTS_URL);
    }
}
