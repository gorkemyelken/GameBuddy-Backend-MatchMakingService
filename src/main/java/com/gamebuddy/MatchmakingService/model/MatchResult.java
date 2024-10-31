package com.gamebuddy.MatchmakingService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchResult {
    @Id
    private String matchResultId;
    private String matchRequestId;
    private List<String> matchedUserIds;
    private String gameId;
    private LocalDateTime createdAt;
}
