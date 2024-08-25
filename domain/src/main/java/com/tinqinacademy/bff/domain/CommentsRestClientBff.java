package com.tinqinacademy.bff.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.restexport.CommentsClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentsRestClientBff {
    private final ApplicationContext applicationContext;
    private final OkHttpClient client;

    @Value("${comments.client.url}")
    private String COMMENTS_URL;

    @Bean
    public CommentsClient getCommentsRestClient() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return Feign.builder()
                .client(client)
                .encoder(new JacksonEncoder(applicationContext.getBean(ObjectMapper.class)))
                .decoder(new JacksonDecoder(applicationContext.getBean(ObjectMapper.class)))
                .target(CommentsClient.class, COMMENTS_URL);
    }
}
