package com.tinqinacademy.bff.domain.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {
    private final ObjectMapper objectMapper;

    @Bean
    public HotelRestExport hotelRestExportClient() {
        return Feign.builder()
                .client(okHttpClient())
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .logger(new Slf4jLogger(HotelRestExport.class))
                .target(HotelRestExport.class, "http://localhost:8080");
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
