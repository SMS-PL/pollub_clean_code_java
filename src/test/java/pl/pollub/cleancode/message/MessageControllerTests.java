package pl.pollub.cleancode.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.Sort;
import pl.pollub.cleancode.domain.message.MessageController;
import pl.pollub.cleancode.domain.message.service.MessageService;
import pl.pollub.cleancode.domain.message.enums.MessageType;
import pl.pollub.cleancode.infrastructure.cron.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.argThat;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.test.context.ActiveProfiles("test")
@WebMvcTest(MessageController.class)
class MessageControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockitoBean private MessageService messageService;
    @MockitoBean private CronRepository cronRepository;

    @BeforeEach
    void setUp() {
        when(messageService.getSupportedTypes()).thenReturn(List.of(MessageType.PAYMENT));
        when(cronRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))).thenReturn(List.of());
    }

    @Test
    void rendersMessageForm() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("action=\"/messages\"")))
                .andExpect(content().string(containsString("name=\"message\"")))
                .andExpect(content().string(containsString("Wyślij")))
                .andExpect(content().string(containsString("name=\"type\"")))
                .andExpect(content().string(containsString("PAYMENT")))
                .andExpect(content().string(containsString("SHIPPING — niedostępny")))
                .andExpect(content().string(containsString("Brak zadań cron")));
    }

    @Test
    void rendersStoredCronsWithEscapedContent() throws Exception {
        when(cronRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))).thenReturn(List.of(
                CronEntity.builder().id(42L).cronType(CronTypeAction.SEND_EMAIL)
                        .status(CronStatus.QUEUED).emailAddress("test@example.com")
                        .content("<script>alert('test')</script>").build()));
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("#42 · SEND_EMAIL")))
                .andExpect(content().string(containsString("W kolejce")))
                .andExpect(content().string(containsString("test@example.com")))
                .andExpect(content().string(containsString("&lt;script&gt;")));
    }

    @Test
    void acceptsMessageAndRedirectsToForm() throws Exception {
        mvc.perform(post("/messages").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("message", "Cześć, to test!").param("type", "PAYMENT"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(messageService).saveMessage(argThat(request ->
                request.getType() == MessageType.PAYMENT && "Cześć, to test!".equals(request.getMessage())));
    }
}
