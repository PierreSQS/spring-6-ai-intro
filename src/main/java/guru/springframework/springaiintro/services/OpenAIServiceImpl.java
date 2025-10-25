package guru.springframework.springaiintro.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.GetCapitalRequest;
import guru.springframework.springaiintro.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Modified by Pierrot on 25.10.2025.
 */
@Service
public class OpenAIServiceImpl implements OpenAIService {

    Logger logger = Logger.getLogger(getClass().getName());

    private final ChatClient chatClient;

    private final ObjectMapper objectMapper;

    public OpenAIServiceImpl(ChatClient.Builder chatClientBuilder, ObjectMapper objectMapper) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalWithInfoPrompt;


    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalWithInfoPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        assert response != null;
        return new Answer(response.getResult().getOutput().getText());
    }

    @Override
    public Answer getCapital(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        assert response != null;
        String content = response.getResult().getOutput().getText();
        logger.info(content);

        String responString;

        try {
            JsonNode jsonNode = objectMapper.readTree(content);
            responString = jsonNode.get("answer").asText();

        } catch (JsonProcessingException jsonException) {
            throw new RuntimeException("Error parsing response", jsonException);
        }

        return new Answer(responString);
    }

    @Override
    public Answer getAnswer(Question question) {
        logger.info("I was called");

        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        assert response != null;
        return new Answer(response.getResult().getOutput().getText());
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        assert response != null;
        return response.getResult().getOutput().getText();
    }
}
