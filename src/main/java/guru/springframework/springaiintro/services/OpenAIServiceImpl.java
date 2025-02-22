package guru.springframework.springaiintro.services;

import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.GetCapitalRequest;
import guru.springframework.springaiintro.model.GetCapitalResponse;
import guru.springframework.springaiintro.model.GetCapitalWithInfoResponse;
import guru.springframework.springaiintro.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Modified by Pierrot on 22-02-2025.
 */
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private static final Logger logger = LoggerFactory.getLogger(OpenAIServiceImpl.class);

    private final ChatModel chatModel;

    public OpenAIServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalPromptWithInfo;

    @Override
    public GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        BeanOutputConverter<GetCapitalWithInfoResponse> converter = new BeanOutputConverter<>(GetCapitalWithInfoResponse.class);
        String responseFormat = converter.getFormat();
        displayResponseFormat(responseFormat);

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptWithInfo);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(),
                "responseFormat", responseFormat));
        ChatResponse response = chatModel.call(prompt);

        logger.info(response.getResult().getOutput().getText());

        return converter.convert(response.getResult().getOutput().getText());
    }

    @Override
    public GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest) {
        BeanOutputConverter<GetCapitalResponse> converter =
                new BeanOutputConverter<>(GetCapitalResponse.class);

        String respFormat = converter.getFormat();
        displayResponseFormat(respFormat);

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(),
                "responseFormat", respFormat));

        ChatResponse response = chatModel.call(prompt);

        logger.info(response.getResult().getOutput().getText());

        return  converter.convert(response.getResult().getOutput().getText());
    }

    @Override
    public Answer getAnswer(Question question) {
        logger.info("I was called");

        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getText());
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getText();
    }

    private static void displayResponseFormat(String respFormat) {
        logger.info("Response format: {}", respFormat);
    }

}
