package com.example.springaidemo.controller;

import com.example.springaidemo.helper.Helper;
import com.example.springaidemo.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping()
public class ChatController {

    private ChatService chatService;

    public ChatController(ChatService chatService){
        this.chatService = chatService;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam String query){
        return ResponseEntity.ok(chatService.chat(query));
    }

    @GetMapping("/chat/promptTemplate")
    public ResponseEntity<String> chatTemplateWithPromptTemplate(){
        return ResponseEntity.ok(chatService.chatTemplateWithPromptTemplate("Spring", "spring exception"));
    }

    @GetMapping("/chat/fluentApi")
    public ResponseEntity<String> chatTemplateWithFluentApi(){
        return ResponseEntity.ok(chatService.chatTemplateWithFluentApi("Spring", "spring exception"));
    }

    @GetMapping("/chat/resourceFile")
    public ResponseEntity<String> chatTemplateWithResourceFile(){
        return ResponseEntity.ok(chatService.chatTemplateWithResourceFile());
    }

    @GetMapping("/chat/stream")
    public ResponseEntity<Flux<String>> chatStream(@RequestParam String query){
        return ResponseEntity.ok(chatService.chatStream(query));
    }

    @GetMapping("/chat/memory")
    public ResponseEntity<String> chatMemory(@RequestParam String query, @RequestParam String conversationId){
        return ResponseEntity.ok(chatService.chatMemory(query, conversationId));
    }

    @GetMapping("/rag/saveData")
    public ResponseEntity<String> ragSaveData(){
        chatService.saveData(Helper.getData());
        return ResponseEntity.ok("Data saved successfully");
    }
}
