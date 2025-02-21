package guru.springframework.springaiintro.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.GetCapitalRequest;
import guru.springframework.springaiintro.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * Modified by Pierrot on 21.02.2025.
 */
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private static final Logger logger = LoggerFactory.getLogger(OpenAIServiceImpl.class);

    private final ChatModel chatModel;

    private final ObjectMapper objectMapper;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPromptResource;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalWithInfoPromptResource;


    public OpenAIServiceImpl(ChatModel chatModel, ObjectMapper objectMapper) {
        this.chatModel = chatModel;
        this.objectMapper = objectMapper;
    }


    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalWithInfoPromptResource);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatResponse response = chatModel.call(prompt);

        return new Answer(Objects.requireNonNull(response).getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapital(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptResource);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatResponse response = chatModel.call(prompt);

        logger.info("### the content of the Response: {} ###",response.getResult().getOutput().getContent());
        String responseString;

        try {
            JsonNode jsonNode = objectMapper.readTree(response.getResult().getOutput().getContent());
            responseString = jsonNode.get("answer").asText();
            logger.info("### the responseString: {} ###",responseString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new Answer(responseString);
    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatModel.call(prompt);

        return new Answer(Objects.requireNonNull(response).getResult().getOutput().getContent());
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatModel.call(prompt);

        assert response != null;
        return response.getResult().getOutput().getContent();
    }
}
