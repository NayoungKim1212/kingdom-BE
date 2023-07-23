package sparta.kingdombe.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import sparta.kingdombe.domain.chat.dto.ChatRoomDto;
import sparta.kingdombe.domain.chat.model.ChatMessage;
import sparta.kingdombe.domain.chat.model.ChatRoom;
import sparta.kingdombe.domain.chat.repository.ChatMessageRepository;
import sparta.kingdombe.domain.chat.repository.ChatRoomRepository;
import sparta.kingdombe.domain.chat.service.ChatRoomService;
import sparta.kingdombe.domain.chat.service.ChatService;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.domain.user.repository.UserRepository;
import sparta.kingdombe.global.jwt.JwtProvider;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController("/api/chat")
public class ChatController {

    private final JwtProvider jwtProvider;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/rooms")
    public List<ChatRoom> room() {
        List<ChatRoom> chatRooms = chatRoomService.finaAllRoom();
        chatRooms.stream().forEach(room -> room.setUserCount(chatRoomService.getUserCount(room.getRoomId())));
        return chatRooms;
    }

    @PostMapping("/room")
    public ChatRoom createRoom(@RequestBody ChatRoomDto chatRoomDto) {
        ChatRoom createdRoom = chatRoomService.createChatRoom(chatRoomDto);
        chatRoomRepository.save(createdRoom);
        return createdRoom;
    }

    @GetMapping("/room/{roomId}")
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomService.findRoomById(roomId);
    }

    /**
     * "/pub/chat/message"로 들어오는 메시징 처리
     */
    @GetMapping("/message/{roomId}")
    public List<ChatMessage> loadMessage(@PathVariable String roomId) {
        List<ChatMessage> messages = chatMessageRepository.findAllByRoomId(roomId);
        return messages;
    }

    @MessageMapping("/message")
    public void message(@RequestBody ChatMessage message, @Header("token") String token) {
        String email = jwtProvider.getUserPk(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 E-mail이 없습니다"));

        String nickname = user.getUsername();
        message.setUsername(nickname);
        message.setUserCount(chatRoomService.getUserCount(message.getRoomId()));

        chatService.sendChatMessage(message);
        chatMessageRepository.save(message);
    }
}
