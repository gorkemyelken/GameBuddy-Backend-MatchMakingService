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
    private String id;
    private String userId;
    private String gameId;
    private List<String> preferredRanks; // Tercih edilen rütbeler
    private List<Integer> ages;           // Tercih edilen yaşlar
    private List<String> genders;         // Tercih edilen cinsiyetler
    private Float rating;                  // Kullanıcı puanı
    private LocalDateTime createdAt;      // Oluşturulma tarihi
}
