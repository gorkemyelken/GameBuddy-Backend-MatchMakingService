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
public class MatchRequest {
    @Id
    private String id;
    private String userId;
    private String gameId;
    private List<String> preferredRanks;
    private int minAge;
    private int maxAge;
    private List<String> genders;
    private Float rating;
    private LocalDateTime createdAt;
}
