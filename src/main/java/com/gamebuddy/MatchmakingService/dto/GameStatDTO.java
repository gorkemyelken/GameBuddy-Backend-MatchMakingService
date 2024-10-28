package com.gamebuddy.MatchmakingService.dto;

import lombok.Data;

@Data
public class GameStatDTO {
    private String id;
    private String gameId;
    private String userId;
    private String gameRank;
}
