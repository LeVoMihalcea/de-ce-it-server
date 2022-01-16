package com.leomihalcea.deceitserver.controller;

import com.leomihalcea.deceitserver.model.IntegerWrapper;
import com.leomihalcea.deceitserver.model.User;
import com.leomihalcea.deceitserver.storage.RedissonStorage;
import org.redisson.api.RMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RestController
@RequestMapping("/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final RedissonStorage redissonStorage;

    public UserController(RedissonStorage redissonStorage) {
        this.redissonStorage = redissonStorage;
    }

    @PostMapping
    public User login(@RequestBody User user) {
        RMap<String, List<String>> teamUsersMap = redissonStorage.getTeamUsersMap();
        if (teamUsersMap.containsKey(user.getTeam())) {
            List<String> usersInTeam = teamUsersMap.get(user.getTeam());
            if (!usersInTeam.contains(user.getName())) {
                usersInTeam.add(user.getName());
            }
        } else {
            redissonStorage.getTeamPointsMap().put(user.getTeam(), 0);
            teamUsersMap.put(user.getTeam(), Arrays.asList(user.getName()));
        }
        return user;
    }

    @PostMapping(value = "/{username}/click")
    public IntegerWrapper buttonPressed(@PathVariable String username, @RequestBody IntegerWrapper integerWrapper) {
        RMap<String, Integer> reactionTimes = redissonStorage.getReactionTimes();
        reactionTimes.put(username, integerWrapper.getValue());
        return integerWrapper;
    }

    @GetMapping(value = "/reaction")
    public Map<String, Integer> getReactions() {
        RMap<String, Integer> reactionTimes = redissonStorage.getReactionTimes();
        return reactionTimes.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
