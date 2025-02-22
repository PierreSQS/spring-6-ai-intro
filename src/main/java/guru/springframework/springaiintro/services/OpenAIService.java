package guru.springframework.springaiintro.services;

import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.GetCapitalRequest;
import guru.springframework.springaiintro.model.GetCapitalResponse;
import guru.springframework.springaiintro.model.GetCapitalWithInfoResponse;
import guru.springframework.springaiintro.model.Question;

/**
 * Modified by Pierrot on 22-02-2025.
 */
public interface OpenAIService {

    GetCapitalWithInfoResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest);
    GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest);
    String getAnswer(String question);

    Answer getAnswer(Question question);
}
