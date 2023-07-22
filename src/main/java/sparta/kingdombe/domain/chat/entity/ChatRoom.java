package sparta.kingdombe.domain.chat.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import sparta.kingdombe.domain.chat.dto.ChatRoomRequestDto;
import sparta.kingdombe.domain.user.entity.User;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static org.hibernate.annotations.OnDeleteAction.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class ChatRoom {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    private String title;
    private String username;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = CASCADE)
    private User user;


    public ChatRoom(ChatRoomRequestDto chatRoomRequestDto, User user) {
        this.title = chatRoomRequestDto.getTitle();
        this.username = user.getUsername();
        this.user = user;

    }
}
