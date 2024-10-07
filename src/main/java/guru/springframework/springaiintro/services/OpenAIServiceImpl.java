package guru.springframework.springaiintro.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OpenAIServiceImpl implements OpenAIService{

    private final ChatClient chatGPTClient;

    public OpenAIServiceImpl(ChatClient chatGPTClient) {
        this.chatGPTClient = chatGPTClient;
    }

    @Override
    public String getAnswer(String question) {
        return this.chatGPTClient
                .prompt()
                .user(question)
                .call()
                .content();
    }
}
