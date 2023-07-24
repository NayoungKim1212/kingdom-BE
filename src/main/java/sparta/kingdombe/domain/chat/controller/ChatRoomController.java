package sparta.kingdombe.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sparta.kingdombe.domain.chat.dto.ChatRoomDto;
import sparta.kingdombe.domain.chat.model.ChatRoom;
import sparta.kingdombe.domain.chat.repository.ChatRoomRepository;
import sparta.kingdombe.domain.chat.service.ChatRoomService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;


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

}
