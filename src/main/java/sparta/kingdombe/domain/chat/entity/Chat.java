package sparta.kingdombe.domain.chat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.user.entity.User;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Getter
@NoArgsConstructor
@Entity
public class Chat {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String username;
    private String message;
    private LocalDate date;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Chat(ChatRoom chatRoom, String message, User user) {
        this.chatRoom = chatRoom;
        this.username = user.getUsername();
        this.message = message;
        this.date = LocalDate.now();
        this.user = user;
    }
}
