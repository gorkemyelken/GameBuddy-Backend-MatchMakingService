package com.gamebuddy.MatchmakingService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchRequestCreateDTO {
    private String userId;
    private String gameId;
    private List<String> preferredRanks;
    private int minAge;
    private int maxAge;
    private List<String> genders;
    private Float rating;
}
