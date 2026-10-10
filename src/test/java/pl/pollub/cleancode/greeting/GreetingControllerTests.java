package pl.pollub.cleancode.greeting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.test.context.ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerTests {
    @Autowired MockMvc mvc;

    @Test
    void returnsGreeting() throws Exception {
        mvc.perform(get("/api/v1/greetings"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.message").value("API działa!"));
    }

    @Test
    void acceptsName() throws Exception {
        mvc.perform(post("/api/v1/greetings").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\" Ala \"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.message").value("Cześć, Ala!"));
    }

    @Test
    void rejectsBlankName() throws Exception {
        mvc.perform(post("/api/v1/greetings").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\" \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.errors[0].field").value("name"));
    }

    @Test
    void allowsFrontendPreflight() throws Exception {
        mvc.perform(options("/api/v1/greetings").header("Origin", "http://localhost:5173")
                .header("Access-Control-Request-Method", "POST")
                .header("Access-Control-Request-Headers", "content-type"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:5173"));
    }

    @Test
    void rejectsUnknownOrigin() throws Exception {
        mvc.perform(options("/api/v1/greetings").header("Origin", "https://unknown.example")
                .header("Access-Control-Request-Method", "POST"))
                .andExpect(status().isForbidden());
    }
}
