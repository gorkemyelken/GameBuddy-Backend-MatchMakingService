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
public class MatchResultCreateDTO {
    private String matchRequestId;              // Eşleşme talebi ID'si
    private List<String> matchedUserIds;        // Eşleşen kullanıcılar
    private String gameId;                      // Oyun ID'si
}
