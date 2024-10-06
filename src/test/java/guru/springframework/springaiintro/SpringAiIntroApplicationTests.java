package guru.springframework.springaiintro;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SpringAiIntroApplicationTests {

    @Test
    void contextLoads(ApplicationContext appCtx) {
        assertThat(appCtx).isNotNull();
    }

}
