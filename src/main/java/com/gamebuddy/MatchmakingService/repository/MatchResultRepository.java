package com.gamebuddy.MatchmakingService.repository;

import com.gamebuddy.MatchmakingService.model.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchResultRepository extends JpaRepository<MatchResult, String> {
    List<MatchResult> findByMatchRequestId(String matchRequestId);

    List<MatchResult> findByGameId(String gameId);

    List<MatchResult> findByMatchedUserIdsIn(List<String> matchedUserIds);
}
