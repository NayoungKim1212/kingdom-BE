package sparta.kingdombe.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.kingdombe.domain.chat.model.ChatMessage;

import java.util.List;
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByRoomId(String roomId);
}
