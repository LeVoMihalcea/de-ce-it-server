package com.leomihalcea.deceitserver.controller;

import com.leomihalcea.deceitserver.model.JeopardyTile;
import com.leomihalcea.deceitserver.model.StringWrapper;
import com.leomihalcea.deceitserver.storage.RedissonStorage;
import org.redisson.api.RMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RestController
@RequestMapping("/v1/jeopardy")
public class JeopardyController {

    private final RedissonStorage redissonStorage;

    public JeopardyController(RedissonStorage redissonStorage) {
        this.redissonStorage = redissonStorage;
    }

    @GetMapping
    public Map<String, Map<String, JeopardyTile>> getBoard() {
        RMap<String, Map<String, JeopardyTile>> teamPointsMap = redissonStorage.getJeopardyTileMap();
        return teamPointsMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @PostMapping
    public StringWrapper addCategory(@RequestBody StringWrapper stringWrapper) {
        RMap<String, Map<String, JeopardyTile>> jeopardyTileMap = redissonStorage.getJeopardyTileMap();
        if (!jeopardyTileMap.containsKey(stringWrapper.getValue())) {
            jeopardyTileMap.put(stringWrapper.getValue(), new HashMap<>());
        }
        return stringWrapper;
    }

    @PutMapping("/{category}")
    public void updateCategoryName(@PathVariable String category) {
        RMap<String, Map<String, JeopardyTile>> jeopardyTileMap = redissonStorage.getJeopardyTileMap();
        jeopardyTileMap.put(category, jeopardyTileMap.get(category));
        jeopardyTileMap.remove(category);
    }

    @DeleteMapping("/{category}")
    public void removeCategory(@PathVariable String category) {
        RMap<String, Map<String, JeopardyTile>> jeopardyTileMap = redissonStorage.getJeopardyTileMap();
        jeopardyTileMap.remove(category);
    }

    @PostMapping("/{category}/{tile}")
    public JeopardyTile addOrUpdate(@PathVariable String category, @PathVariable String tile, @RequestBody JeopardyTile body) {
        RMap<String, Map<String, JeopardyTile>> jeopardyTileMap = redissonStorage.getJeopardyTileMap();
        if (!jeopardyTileMap.containsKey(category)) {
            jeopardyTileMap.put(category, new HashMap<>());
        }
        Map<String, JeopardyTile> stringJeopardyTileMap = jeopardyTileMap.get(category);
        stringJeopardyTileMap.put(tile, body);
        jeopardyTileMap.put(category, stringJeopardyTileMap);
        return body;
    }

    @PostMapping("/{category}/{tile}/deactivate")
    public void deactivate(@PathVariable String category, @PathVariable String tile) {
        RMap<String, Map<String, JeopardyTile>> jeopardyTileMap = redissonStorage.getJeopardyTileMap();
        Map<String, JeopardyTile> stringJeopardyTileMap = jeopardyTileMap.get(category);
        stringJeopardyTileMap.get(tile).setActive(false);
        jeopardyTileMap.put(category, stringJeopardyTileMap);

    }

    @PostMapping("/{category}/{tile}/activate")
    public void activate(@PathVariable String category, @PathVariable String tile) {
        RMap<String, Map<String, JeopardyTile>> jeopardyTileMap = redissonStorage.getJeopardyTileMap();
        Map<String, JeopardyTile> stringJeopardyTileMap = jeopardyTileMap.get(category);
        stringJeopardyTileMap.get(tile).setActive(true);
        jeopardyTileMap.put(category, stringJeopardyTileMap);
    }

    @DeleteMapping("/{category}/{tile}")
    public void deleteTile(@PathVariable String category, @PathVariable String tile) {
        RMap<String, Map<String, JeopardyTile>> jeopardyTileMap = redissonStorage.getJeopardyTileMap();
        Map<String, JeopardyTile> stringJeopardyTileMap = jeopardyTileMap.get(category);
        stringJeopardyTileMap.remove(tile);
        jeopardyTileMap.put(category, stringJeopardyTileMap);
    }

    @DeleteMapping
    public void deleteJeopardy() {
        redissonStorage.getJeopardyTileMap().clear();
    }

}
