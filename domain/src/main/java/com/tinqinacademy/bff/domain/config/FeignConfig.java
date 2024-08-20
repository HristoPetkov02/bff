package com.tinqinacademy.bff.domain.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.auth.restexport.AuthRestExport;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {
    private final ObjectMapper objectMapper;
    @Value("${hotel.feign.url}")
    private String hotelFeignUrl;

    @Value("${auth.feign.url}")
    private String authFeignUrl;

    @Value("${comments.feign.url}")
    private String commentsFeignUrl;

    @Bean
    public HotelRestExport hotelRestExportClient() {
        return Feign.builder()
                .client(okHttpClient())
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .logger(new Slf4jLogger(HotelRestExport.class))
                .target(HotelRestExport.class, hotelFeignUrl);
    }

    @Bean
    public AuthRestExport authRestExportClient() {
        return Feign.builder()
                .client(okHttpClient())
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .logger(new Slf4jLogger(AuthRestExport.class))
                .target(AuthRestExport.class, authFeignUrl);
    }

    @Bean
    public CommentsRestExport commentsRestExportClient() {
        return Feign.builder()
                .client(okHttpClient())
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .logger(new Slf4jLogger(CommentsRestExport.class))
                .target(CommentsRestExport.class, commentsFeignUrl);
    }



    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
