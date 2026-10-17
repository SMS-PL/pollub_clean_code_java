package pl.pollub.cleancode.domain.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pollub.cleancode.domain.message.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
