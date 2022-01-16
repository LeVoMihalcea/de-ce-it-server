package com.leomihalcea.deceitserver.storage;

import com.leomihalcea.deceitserver.model.JeopardyTile;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RedissonStorage {

    private final RedissonClient redissonClient;

    public RedissonStorage(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public RMap<String, List<String>> getTeamUsersMap() {
        return redissonClient.getMap("teamUsers");
    }

    public RMap<String, Integer> getTeamPointsMap() {
        return redissonClient.getMap("teamPoints");
    }

    public RMap<String, Integer> getReactionTimes() {
        return redissonClient.getMap("reactionTimes");
    }

    public RMap<String, Map<String, JeopardyTile>> getJeopardyTileMap() {
        return redissonClient.getMap("teamPoints");
    }

    public void clearReactionTimes() {
        getReactionTimes().clear();
    }
}
