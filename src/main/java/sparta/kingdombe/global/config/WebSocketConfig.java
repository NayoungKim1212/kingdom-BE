package sparta.kingdombe.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    /**
     * 메서드 기능 - 접속주소 설정, CORS 설정, SocketJS 연결 설정
     * @param registry 통신 가능한 지점을 등록하여 데이터를 주고받는 객체
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }


    /**
     * 메서드 기능 - 서버와 클라이언트 간의 메세지 전달을 중재하는 메세지 브로커 설정
     * enableSimpleBroker - /topic/주제 일 때 해당 주제를 구독하는 모든 클라이언트가 메세지 수신
     * setApplicatoinDestinationPrefixes - 클라이언트가 메세지를 특정 주제로 보낼 때 해당 Prefixes로 시작하는 주제를 수신 대상으로 인식
     * 클라이언트는 어떤 주제로 보낼지 약속하고, 서버는 해당 주제로 온 메세지를 받아서 처리할 준비
     *
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}
