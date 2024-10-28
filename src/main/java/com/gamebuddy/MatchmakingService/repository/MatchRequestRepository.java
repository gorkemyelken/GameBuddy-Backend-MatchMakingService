package com.gamebuddy.MatchmakingService.repository;

import com.gamebuddy.MatchmakingService.model.MatchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRequestRepository extends JpaRepository<MatchRequest, String> {
    // Kullanıcı ID'sine göre eşleşme taleplerini bulma
    List<MatchRequest> findByUserId(String userId);

    // Oyun ID'sine göre eşleşme taleplerini bulma
    List<MatchRequest> findByGameId(String gameId);

    // Tercih edilen rütbe ile eşleşen talepleri bulma
    List<MatchRequest> findByPreferredRanksIn(List<String> preferredRanks);
}
