package sparta.kingdombe.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.kingdombe.domain.chat.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
