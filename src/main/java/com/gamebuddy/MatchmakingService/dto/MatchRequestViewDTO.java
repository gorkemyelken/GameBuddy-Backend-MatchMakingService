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
public class MatchRequestViewDTO {
    private String matchRequestId;
    private String userId;
    private String gameId;
    private List<String> preferredRanks;
    private int minAge;
    private int maxAge;
    private List<String> genders;
    private Float minRating;
    private Float maxRating;
    private LocalDateTime createdAt;
}
