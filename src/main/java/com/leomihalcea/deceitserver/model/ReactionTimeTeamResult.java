package com.leomihalcea.deceitserver.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReactionTimeTeamResult {
    private String username;
    private Integer time;
}
