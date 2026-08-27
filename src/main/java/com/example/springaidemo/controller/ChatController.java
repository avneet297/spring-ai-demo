package com.example.springaidemo.controller;

import com.example.springaidemo.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class ChatController {

    private ChatService chatService;

    public ChatController(ChatService chatService){
        this.chatService = chatService;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam String query){
        //    return ResponseEntity.ok(chatService.chat(query));
        //return ResponseEntity.ok(chatService.chatTemplateWithPromptTemplate("Spring", "spring exception"));
        //return ResponseEntity.ok(chatService.chatTemplateWithFluentApi("Spring", "spring exception"));
        return ResponseEntity.ok(chatService.chatTemplateWithResourceFile());
    }
}
