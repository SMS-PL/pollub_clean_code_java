package pl.pollub.cleancode.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pollub.cleancode.message.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
