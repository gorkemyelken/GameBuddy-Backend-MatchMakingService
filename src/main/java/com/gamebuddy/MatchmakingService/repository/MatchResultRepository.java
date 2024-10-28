package com.gamebuddy.MatchmakingService.repository;

import com.gamebuddy.MatchmakingService.model.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchResultRepository extends JpaRepository<MatchResult, String> {
    // Eşleşme talebi ID'sine göre eşleşme sonuçlarını bulma
    List<MatchResult> findByMatchRequestId(String matchRequestId);

    // Oyun ID'sine göre eşleşme sonuçlarını bulma
    List<MatchResult> findByGameId(String gameId);

    // Eşleşen kullanıcı ID'lerine göre eşleşme sonuçlarını bulma
    List<MatchResult> findByMatchedUserIdsIn(List<String> matchedUserIds);
}
