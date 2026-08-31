package com.example.springaidemo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {
    private ChatClient chatClient;

    @Value("classpath:/prompts/user-message.st")
    private Resource userMessage;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemMessage;

    @Value("classpath:/prompts/system-message-stream.st")
    private Resource systemMessageStream;

    public ChatServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String chat(String query) {

        var tutorials = chatClient
                .prompt()
                .user(query)
                .call()
                .content();

        return tutorials;

        /*String queryStr = "As an expert in coding and programing. Always write program in java . Now reply for this question :{query}";

        var tutorials = chatClient
                .prompt()
                .user(u -> u.text(queryStr).param("query", query))
                .call()
                .content();

        return tutorials;*/
    }

    @Override
    public String chatTemplateWithPromptTemplate(String techName, String techExample){
        //first step
        PromptTemplate strTemplate = PromptTemplate.builder().template("What is {techName}? tell ma also about {techExample}").build();

        //render the template
        String renderedMessage = strTemplate.render(Map.of(
                "techName", "Spring",
                "techExample", "spring exception"
        ));

        Prompt prompt = new Prompt(renderedMessage);

        return this.chatClient.prompt(prompt).call().content();
    }

    @Override
    public String chatTemplateWithFluentApi(String techName, String techExample){
        return this.chatClient
                .prompt()
                .system(system ->
                        system.text("You are a helpful coding assistant. You are an expert in coding."))
                .user(user ->
                        user.text("What is {techName}? tell me also about {techExample}")
                                .param("techName", techName)
                                .param("techExample", techExample))
                .call()
                .content();
    }

    @Override
    public String chatTemplateWithResourceFile(){
        return this.chatClient
                .prompt()
                .system(system ->
                        system.text(this.systemMessage))
                .user(user ->
                        user.text(this.userMessage).param("concept", "Java iteration"))
                .call()
                .content();
    }

    @Override
    public Flux<String> chatStream(String query){
        return this.chatClient
                .prompt()
                .system(system ->
                        system.text(this.systemMessageStream))
                .user(user ->
                        user.text(this.userMessage).param("concept", query))
                .stream()
                .content();
    }

    @Override
    public String chatMemory(String query, String conversationId){
        return this.chatClient
                .prompt()
                .user(query)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}
