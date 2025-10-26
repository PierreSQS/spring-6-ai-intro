package guru.springframework.springaiintro.services;

import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.GetCapitalRequest;
import guru.springframework.springaiintro.model.Question;
import org.springframework.http.ResponseEntity;

/**
 * Modified by Pierrot on 26.10.2025.
 */
public interface OpenAIService {

    ResponseEntity<Answer> getCapital(GetCapitalRequest getCapitalRequest);

    String getAnswer(String question);

    Answer getAnswer(Question question);
}
