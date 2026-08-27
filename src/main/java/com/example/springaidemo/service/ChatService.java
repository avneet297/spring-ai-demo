package com.example.springaidemo.service;

public interface ChatService {
    String chat(String query);
    String chatTemplateWithPromptTemplate(String techName, String techExample);
    String chatTemplateWithFluentApi(String techName, String techExample);
    String chatTemplateWithResourceFile();
}
