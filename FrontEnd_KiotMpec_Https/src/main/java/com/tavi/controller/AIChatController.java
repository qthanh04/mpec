package com.tavi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ai-chat")
public class AIChatController {
    
    private final RestTemplate restTemplate;
    
    @Value("${backend.url:http://localhost:8080}")
    private String backendUrl;
    
    public AIChatController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }
    
    @PostMapping("/send")
    @ResponseBody
    public Map<String, String> sendMessageToAI(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        
        try {
            String userMessage = request.get("message");
            
            // Gọi API backend để chat với AI
            String url = backendUrl + "/api/chat";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("message", userMessage);
            
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> aiResponse = restTemplate.postForEntity(url, entity, String.class);
            
            if (aiResponse.getStatusCode().is2xxSuccessful()) {
                response.put("status", "success");
                response.put("response", aiResponse.getBody());
            } else {
                response.put("status", "error");
                response.put("response", "Không thể kết nối đến AI chatbot");
            }
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("response", "Lỗi khi gửi tin nhắn: " + e.getMessage());
        }
        
        return response;
    }
} 