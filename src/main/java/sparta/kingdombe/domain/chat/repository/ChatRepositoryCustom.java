package sparta.kingdombe.domain.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sparta.kingdombe.domain.chat.dto.ChatResponseDto;

public interface ChatRepositoryCustom {
    Page<ChatResponseDto> getChats(Pageable pageable);
}
