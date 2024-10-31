package com.gamebuddy.MatchmakingService.service;

import com.gamebuddy.MatchmakingService.dto.GameStatDTO;
import com.gamebuddy.MatchmakingService.dto.MatchRequestCreateDTO;
import com.gamebuddy.MatchmakingService.dto.MatchResultViewDTO;
import com.gamebuddy.MatchmakingService.dto.UserDTO;
import com.gamebuddy.MatchmakingService.exception.results.DataResult;
import com.gamebuddy.MatchmakingService.exception.results.ErrorDataResult;
import com.gamebuddy.MatchmakingService.exception.results.SuccessDataResult;
import com.gamebuddy.MatchmakingService.model.MatchRequest;
import com.gamebuddy.MatchmakingService.model.MatchResult;
import com.gamebuddy.MatchmakingService.repository.MatchRequestRepository;
import com.gamebuddy.MatchmakingService.repository.MatchResultRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MatchMakingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchMakingService.class);

    private final MatchResultRepository matchResultRepository;

    private final MatchRequestRepository matchRequestRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    @Value("${user.service.url}")
    private String userServiceUrl;
    @Value("${game.service.url}")
    private String gameServiceUrl;

    @Autowired
    public MatchMakingService(MatchResultRepository matchResultRepository, MatchRequestRepository matchRequestRepository, ModelMapper modelMapper, RestTemplate restTemplate) {
        this.matchResultRepository = matchResultRepository;
        this.matchRequestRepository = matchRequestRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    public DataResult<MatchResultViewDTO> findMatch(MatchRequestCreateDTO matchRequestCreateDTO) {
        LOGGER.info("[findMatch] MatchRequestCreateDTO: {}",matchRequestCreateDTO);
        MatchRequest matchRequest = new MatchRequest();
        matchRequest.setMatchRequestId(UUID.randomUUID().toString());
        this.matchRequestRepository.save(matchRequest);

        int minAge = matchRequestCreateDTO.getMinAge();
        int maxAge = matchRequestCreateDTO.getMaxAge();
        List<String> genders = matchRequestCreateDTO.getGenders();
        float minRating = matchRequestCreateDTO.getMinRating();
        float maxRating = matchRequestCreateDTO.getMaxRating();

        String userServiceURL = userServiceUrl + "/users/by-criteria?minAge=" + minAge + "&maxAge=" + maxAge + "&minRating=" + minRating + "&maxRating=" + maxRating + "&genders=" + String.join(",", genders);

        LOGGER.info("[findMatch] userServiceURL: {}",userServiceURL);

        ResponseEntity<DataResult<List<UserDTO>>> userResponse = restTemplate.exchange(
                userServiceURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataResult<List<UserDTO>>>() {}
        );

        LOGGER.info("[findMatch] userResponse: {}",userResponse);

        if (userResponse.getBody() == null || !userResponse.getBody().isSuccess()) {
            return new ErrorDataResult<>("User not found by criterias.");
        }

        List<UserDTO> users = userResponse.getBody().getData();

        LOGGER.info("[findMatch] Found users: {}",users);

        List<String> preferredRanks = matchRequestCreateDTO.getPreferredRanks();
        String gameServiceURL = gameServiceUrl + "/gamestats/by-ranks?ranks=" + String.join(",", preferredRanks);

        LOGGER.info("[findMatch] gameServiceUrl: {}",gameServiceUrl);

        ResponseEntity<DataResult<List<GameStatDTO>>> gameStatResponse = restTemplate.exchange(
                gameServiceURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataResult<List<GameStatDTO>>>() {}
        );

        LOGGER.info("[findMatch] gameStatResponse: {}",gameStatResponse);

        if (gameStatResponse.getBody() == null || !gameStatResponse.getBody().isSuccess()) {
            return new ErrorDataResult<>("User not found by criterias.");
        }

        List<GameStatDTO> gameStats = gameStatResponse.getBody().getData();

        LOGGER.info("[findMatch] Found gameStats: {}",gameStats);

        List<UserDTO> matchedUsers = users.stream()
                .filter(user -> gameStats.stream().anyMatch(stat -> stat.getUserId().equals(user.getUserId())))
                .collect(Collectors.toList());

        LOGGER.info("[findMatch] Matched Users: {}",matchedUsers);

        MatchResult matchResult = new MatchResult();
        matchResult.setMatchResultId(UUID.randomUUID().toString());
        matchResult.setCreatedAt(LocalDateTime.now());
        List<String> matchedUserIds = new ArrayList<>();
        matchedUserIds.add(matchRequestCreateDTO.getUserId());
        matchedUserIds.add(matchedUsers.get(0).getUserId());
        matchResult.setMatchedUserIds(matchedUserIds);
        matchResult.setMatchRequestId(matchRequest.getMatchRequestId());
        matchResult.setGameId(matchRequestCreateDTO.getGameId());

        LOGGER.info("[findMatch] Match Result: {}",matchResult.toString());
        
        this.matchResultRepository.save(matchResult);

        MatchResultViewDTO matchResultViewDTO = modelMapper.map(matchResult, MatchResultViewDTO.class);
        return new SuccessDataResult<>(matchResultViewDTO, "Matched successfully.");
    }

}
