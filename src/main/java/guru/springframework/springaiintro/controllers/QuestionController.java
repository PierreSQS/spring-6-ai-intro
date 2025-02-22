package guru.springframework.springaiintro.controllers;

import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.GetCapitalRequest;
import guru.springframework.springaiintro.model.GetCapitalResponse;
import guru.springframework.springaiintro.model.GetCapitalWithInfoResponse;
import guru.springframework.springaiintro.model.Question;
import guru.springframework.springaiintro.services.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Modified by Pierrot on 22-02-2025.
 */
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/getCapitalWithInfo")
    public GetCapitalWithInfoResponse getCapitalWithInfo(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAIService.getCapitalWithInfo(getCapitalRequest);
    }

    @PostMapping("/getCapital")
    public GetCapitalResponse getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAIService.getCapital(getCapitalRequest);
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }
}
