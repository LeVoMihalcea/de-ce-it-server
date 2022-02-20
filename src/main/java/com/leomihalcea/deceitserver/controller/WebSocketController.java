package com.leomihalcea.deceitserver.controller;

import com.leomihalcea.deceitserver.model.FromAndTime;
import com.leomihalcea.deceitserver.model.WSWikipedia;
import com.leomihalcea.deceitserver.storage.RedissonStorage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final RedissonStorage redissonStorage;

    public WebSocketController(RedissonStorage redissonStorage) {
        this.redissonStorage = redissonStorage;
    }

    @MessageMapping("/ws/wikipedia")
    @SendTo("/socket/wikipedia")
    public WSWikipedia wikipediaStart(WSWikipedia message) {
        redissonStorage.clearReactionTimes();
        return message;
    }

    @MessageMapping("/ws/wikipediaPress")
    @SendTo("/socket/wikipediaPress")
    public FromAndTime wikipediaDone(FromAndTime fromAndTime) {
        redissonStorage.getReactionTimes().put(fromAndTime.getFrom(), fromAndTime.getMs());
        return fromAndTime;
    }

    @MessageMapping("/ws/jeopardyStart")
    @SendTo("/socket/jeopardyStart")
    public String jeopardyReady(String message) {
        return message;
    }

    @MessageMapping("/ws/jeopardyPress")
    @SendTo("/socket/jeopardyPress")
    public FromAndTime jeopardyDone(FromAndTime fromAndTime) {
        redissonStorage.getReactionTimes().put(fromAndTime.getFrom(), fromAndTime.getMs());
        return fromAndTime;
    }

}
