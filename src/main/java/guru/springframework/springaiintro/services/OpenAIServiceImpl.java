package guru.springframework.springaiintro.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * Modified by Pierrot on 2025-02-08
 */
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;

    public OpenAIServiceImpl(ChatClient.Builder chatClientBuilder) {
        // Ensure the correct model name is used here
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String getAnswer(String question) {
        return chatClient.prompt().user(question).call().content();
    }
}
