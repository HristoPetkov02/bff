package com.tinqinacademy.bff.rest.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtDecoder {
    private final ObjectMapper objectMapper;

    public Map<String, Object> decodeJwt(String authorizationHeader) throws IOException {
        String jwt = authorizationHeader.substring(7);
        String[] chunks = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        return objectMapper.readValue(payload, Map.class);
    }
}
