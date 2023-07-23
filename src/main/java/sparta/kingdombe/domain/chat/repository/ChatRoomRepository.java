package sparta.kingdombe.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.kingdombe.domain.chat.model.ChatRoom;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
