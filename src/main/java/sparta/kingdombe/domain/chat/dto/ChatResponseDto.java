package sparta.kingdombe.domain.chat.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ChatResponseDto {

    private Long id;
    private String username;
    private String message;
    private LocalDate date;


    @QueryProjection
    public ChatResponseDto(Long id, String username, String message, LocalDate date) {
        this.id = id;
        this.username = username;
        this.message = message;
        this.date = date;
    }
}
