package sparta.kingdombe.domain.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.chat.entity.ChatRoom;

import java.util.List;

@Getter
@NoArgsConstructor
public class ChatRoomResponseDto {

    private Long id;
    private String title;
    private String username;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.title = chatRoom.getTitle();
        this.username = chatRoom.getUsername();
    }
}
