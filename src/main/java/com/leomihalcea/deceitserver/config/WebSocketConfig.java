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
        registry.addEndpoint("/wikipedia").setAllowedOriginPatterns("**");
        registry.addEndpoint("/wikipedia").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/wikipediaPress").setAllowedOriginPatterns("*");
        registry.addEndpoint("/wikipediaPress").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/jeopardyStart").setAllowedOriginPatterns("*");
        registry.addEndpoint("/jeopardyStart").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/jeopardyPress").setAllowedOriginPatterns("*");
        registry.addEndpoint("/jeopardyPress").setAllowedOriginPatterns("*").withSockJS();
    }
}
