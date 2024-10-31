package com.gamebuddy.MatchmakingService.repository;

import com.gamebuddy.MatchmakingService.model.MatchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRequestRepository extends JpaRepository<MatchRequest, String> {
    List<MatchRequest> findByUserId(String userId);

    List<MatchRequest> findByGameId(String gameId);

    List<MatchRequest> findByPreferredRanksIn(List<String> preferredRanks);
}
