package com.leomihalcea.deceitserver.model;

import lombok.Data;

@Data
public class JeopardyTile {
    private String title;
    private String link;
    private String description;
    private boolean active = true;
}
