package guru.springframework.springaiintro;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringAiIntroApplicationTests {

    @Test
    void contextLoads(ApplicationContext appCtx) {
        System.out.println("Loaded context");
        assertThat(appCtx).isNotNull();
    }

}
