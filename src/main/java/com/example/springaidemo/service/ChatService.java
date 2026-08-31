package com.example.springaidemo.service;

import reactor.core.publisher.Flux;

public interface ChatService {
    String chat(String query);
    String chatTemplateWithPromptTemplate(String techName, String techExample);
    String chatTemplateWithFluentApi(String techName, String techExample);
    String chatTemplateWithResourceFile();
    Flux<String> chatStream(String query);
    String chatMemory(String query, String conversationId);
}
