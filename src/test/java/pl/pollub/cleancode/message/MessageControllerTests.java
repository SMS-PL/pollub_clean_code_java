package pl.pollub.cleancode.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.test.context.ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void rendersMessageForm() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("action=\"/messages\"")))
                .andExpect(content().string(containsString("name=\"message\"")))
                .andExpect(content().string(containsString("Wyślij")));
    }

    @Test
    void acceptsMessageAndRedirectsToForm() throws Exception {
        mvc.perform(post("/messages").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("message", "Cześć, to test!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
