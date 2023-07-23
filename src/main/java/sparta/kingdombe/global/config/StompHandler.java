package sparta.kingdombe.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import sparta.kingdombe.domain.chat.service.ChatRoomService;
import sparta.kingdombe.domain.chat.service.ChatService;
import sparta.kingdombe.domain.user.repository.UserRepository;
import sparta.kingdombe.global.jwt.JwtProvider;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;
    private final UserRepository userRepository;

    /**
     * websocket을 통해 들어온 요청이 처리되기전에 인터셉터를 통해 먼저 실행
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT == accessor.getCommand()) {
            String token = accessor.getFirstNativeHeader("token");
            log.info("token = {}", token);
            jwtProvider.validateToken(token);
        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            String roomId = chatService.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId"));
            log.info("chatRoom = {}", roomId);

            String sessionId = (String) message.getHeaders().get("simpSessionId");

            chatRoomService.setUserEnterInfo(sessionId, roomId);
            chatRoomService.increaseUserCount(roomId);
            String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser")).map(Principal::getName).orElse("UnknownUSer");
            log.info("SUBSCRIBED {}, {}", name, roomId);
        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId =  (String) message.getHeaders().get("simpSessionId");
            String roomId = chatRoomService.getUserEnterRoomId(sessionId);

            chatRoomService.decreaseUserCount(roomId);
            String name = Optional.ofNullable((Principal) message.getHeaders().get("simpleUser")).map(Principal::getName).orElse("UnknownUSer");
            chatRoomService.removeUserEnterInfo(sessionId);
            log.info("DISCONNECTE {}, {}", sessionId, roomId);
        }

        return message;
    }
}
