package com.leomihalcea.deceitserver.controller;

import com.leomihalcea.deceitserver.model.IntegerWrapper;
import com.leomihalcea.deceitserver.storage.RedissonStorage;
import org.redisson.api.RMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RestController
@RequestMapping("/v1/teams")
public class TeamController {

    private final RedissonStorage redissonStorage;

    public TeamController(RedissonStorage redissonStorage) {
        this.redissonStorage = redissonStorage;
    }

    @GetMapping
    public Map<String, Integer> getPoints() {
        RMap<String, Integer> teamPointsMap = redissonStorage.getTeamPointsMap();
        return teamPointsMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @PostMapping("/{team}")
    public IntegerWrapper modifyPoints(@PathVariable String team, @RequestBody IntegerWrapper integerWrapper) {
        RMap<String, Integer> teamPointsMap = redissonStorage.getTeamPointsMap();
        if (teamPointsMap.containsKey(team)) {
            teamPointsMap.put(team, teamPointsMap.get(team) + integerWrapper.getValue());
        } else {
            teamPointsMap.put(team, integerWrapper.getValue());
        }
        return integerWrapper;
    }

    @PostMapping("/reset")
    public void resetPoints() {
        RMap<String, Integer> teamPointsMap = redissonStorage.getTeamPointsMap();
        teamPointsMap.keySet().forEach(s -> teamPointsMap.put(s, 0));
    }

    @DeleteMapping
    public void deleteTeams() {
        redissonStorage.getTeamPointsMap().clear();
        redissonStorage.getTeamUsersMap().clear();
        redissonStorage.getReactionTimes().clear();
    }

}
