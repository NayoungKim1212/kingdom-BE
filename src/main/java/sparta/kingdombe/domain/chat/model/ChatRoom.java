package sparta.kingdombe.domain.chat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import sparta.kingdombe.domain.chat.dto.ChatRoomDto;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    private String roomName;

    private long userCount;

    public static ChatRoom create(ChatRoomDto chatRoomDto) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = chatRoomDto.getRoomName();
        return chatRoom;
    }
}
