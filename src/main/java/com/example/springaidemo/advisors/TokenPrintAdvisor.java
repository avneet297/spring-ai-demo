package com.example.springaidemo.advisors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import reactor.core.publisher.Flux;

public class TokenPrintAdvisor implements CallAdvisor, StreamAdvisor {

    private Logger logger = LoggerFactory.getLogger(TokenPrintAdvisor.class);

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        this.logger.info("TokenPrintAdvisor is called!");
        this.logger.info("Request content: " + chatClientRequest.prompt().getContents());

        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);

        this.logger.info("TokenPrintAdvisor response is received!");
        this.logger.info("Response content: " + chatClientResponse.chatResponse().getResult().getOutput().getText());
        this.logger.info("Total tokens used: " + chatClientResponse.chatResponse().getMetadata().getUsage().getTotalTokens());

        return chatClientResponse;
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {
        Flux<ChatClientResponse> chatClientResponseFlux = streamAdvisorChain.nextStream(chatClientRequest);
        return chatClientResponseFlux;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
