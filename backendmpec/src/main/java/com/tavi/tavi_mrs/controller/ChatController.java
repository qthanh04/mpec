package com.tavi.tavi_mrs.controller;

import com.tavi.tavi_mrs.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
        private final ChatService chatbotService;

        public ChatController(ChatService chatbotService) {
            this.chatbotService = chatbotService;
        }

        @PostMapping
        public ResponseEntity<String> chat(@RequestBody Map<String, String> input) {
            String userInput = input.get("message");
            String response = chatbotService.chatWithQwen(userInput);
            return ResponseEntity.ok(response);
        }
}
