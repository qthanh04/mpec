package com.tavi.tavi_mrs.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatService {
    private final RestTemplate restTemplate;

    public ChatService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public String chatWithQwen(String userInput) {
        String url = "http://localhost:9090/chat";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request = new HashMap<>();
        request.put("input", userInput);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().get("response").toString();
        } else {
            return "Lỗi từ mô hình.";
        }
    }

}
