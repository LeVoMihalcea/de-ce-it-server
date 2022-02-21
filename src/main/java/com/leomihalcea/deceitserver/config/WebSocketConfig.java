package com.leomihalcea.deceitserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/socket");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/wikipedia").setAllowedOriginPatterns("**");
        registry.addEndpoint("/ws/wikipedia").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/ws/wikipediaPress").setAllowedOriginPatterns("*");
        registry.addEndpoint("/ws/wikipediaPress").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/ws/jeopardyStart").setAllowedOriginPatterns("*");
        registry.addEndpoint("/ws/jeopardyStart").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/ws/jeopardyPress").setAllowedOriginPatterns("*");
        registry.addEndpoint("/ws/jeopardyPress").setAllowedOriginPatterns("*").withSockJS();
    }
}
