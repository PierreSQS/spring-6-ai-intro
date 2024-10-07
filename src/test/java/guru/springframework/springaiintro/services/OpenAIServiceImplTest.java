package guru.springframework.springaiintro.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OpenAIServiceImplTest {

    public static final String QUESTION = "What is the biggest city from Cameroon?";

    @Autowired
    OpenAIService openAIServ;

    @Test
    void getAnswer() {
        String answer = openAIServ.getAnswer(QUESTION);
        assertThat(answer).contains("Cameroon");
    }
}