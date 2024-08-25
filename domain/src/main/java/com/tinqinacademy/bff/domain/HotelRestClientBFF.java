package com.tinqinacademy.bff.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.restexport.HotelClient;
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
public class HotelRestClientBFF {
    private final ApplicationContext applicationContext;
    private final OkHttpClient client;

    @Value("${hotel.client.url}")
    private String HOTEL_URL;

    @Bean
    public HotelClient getHotelRestClient() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return Feign.builder()
                .client(client)
                .encoder(new JacksonEncoder(applicationContext.getBean(ObjectMapper.class)))
                .decoder(new JacksonDecoder(applicationContext.getBean(ObjectMapper.class)))
                .target(HotelClient.class, HOTEL_URL);
    }
}
