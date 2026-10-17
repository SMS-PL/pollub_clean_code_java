package pl.pollub.cleancode.message;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.pollub.cleancode.domain.message.entity.Message;
import pl.pollub.cleancode.domain.message.repository.MessageRepository;

import static org.assertj.core.api.Assertions.assertThat;

@org.springframework.test.context.ActiveProfiles("test")
@SpringBootTest
@Transactional
class MessageRepositoryTests {
    @Autowired private MessageRepository repository;
    @Autowired private EntityManager entityManager;

    @Test
    void savesAndReadsMessageFromDatabase() {
        Message message = new Message();
        message.setMessage("Wiadomość testowa");
        Message saved = repository.saveAndFlush(message);
        Long id = saved.getId();
        entityManager.clear();
        assertThat(id).isNotNull();
        assertThat(repository.findById(id)).get()
                .extracting(Message::getMessage).isEqualTo("Wiadomość testowa");
    }
}
