package sparta.kingdombe.domain.chat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ChatMessage {

    public enum MessageType {
        ENTER, QUIT, TALK
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MessageType type;

    private String roomId;

    private String username;

    private String message;

    private long userCount;

    public ChatMessage() {

    }

    @Builder
    public ChatMessage(MessageType type, String roomId, String username, String message, long userCount) {
        this.type = type;
        this.roomId = roomId;
        this.username = username;
        this.message = message;
        this.userCount = userCount;
    }
}
