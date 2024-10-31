package com.gamebuddy.MatchmakingService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchResultViewDTO {
    private String matchResultId;
    private String matchRequestId;
    private List<String> matchedUserIds;
    private String gameId;
    private LocalDateTime createdAt;
}