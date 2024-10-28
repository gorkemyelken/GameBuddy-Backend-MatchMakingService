package com.gamebuddy.MatchmakingService.service;

import com.gamebuddy.MatchmakingService.dto.*;
import com.gamebuddy.MatchmakingService.exception.MatchRequestNotFoundException;
import com.gamebuddy.MatchmakingService.model.MatchRequest;
import com.gamebuddy.MatchmakingService.repository.MatchRequestRepository;
import org.modelmapper.ModelMapper;
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

    private final MatchRequestRepository matchRequestRepository;
    private final ModelMapper modelMapper;

    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    @Value("${game.service.url}")
    private String gameServiceUrl;

    @Autowired
    public MatchMakingService(MatchRequestRepository matchRequestRepository, ModelMapper modelMapper, RestTemplate restTemplate) {
        this.matchRequestRepository = matchRequestRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    public MatchResultViewDTO findMatch(MatchRequestCreateDTO matchRequestCreateDTO) {
        MatchRequest matchRequest = modelMapper.map(matchRequestCreateDTO, MatchRequest.class);
        matchRequest.setId(UUID.randomUUID().toString());
        matchRequest.setCreatedAt(LocalDateTime.now());
        int minAge = matchRequest.getMinAge();
        int maxAge = matchRequest.getMaxAge();
        List<String> genders = matchRequest.getGenders();

        String userServiceURL = userServiceUrl + "/users/by-criteria?minAge=" + minAge + "&maxAge=" + maxAge + "&genders=" + String.join(",", genders);

        ResponseEntity<List<UserDTO>> userResponse = restTemplate.exchange(
                userServiceURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDTO>>() {}
        );

        List<UserDTO> users = userResponse.getBody();

        List<String> preferredRanks = matchRequestCreateDTO.getPreferredRanks();
        String gameServiceURL = gameServiceUrl + "/gamestats/by-ranks?ranks=" + String.join(",", preferredRanks);

        ResponseEntity<List<GameStatDTO>> gameStatResponse = restTemplate.exchange(
                gameServiceURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GameStatDTO>>() {}
        );

        List<GameStatDTO> gameStats = gameStatResponse.getBody();

        List<UserDTO> matchedUsers = users.stream()
                .filter(user -> gameStats.stream().anyMatch(stat -> stat.getUserId().equals(user.getId())))
                .collect(Collectors.toList());

        MatchResultViewDTO matchResult = new MatchResultViewDTO();
        matchResult.setId(UUID.randomUUID().toString());
        matchResult.setMatchRequestId(matchRequest.getId());
        matchResult.setCreatedAt(LocalDateTime.now());

        List<String> matchedUserIds = new ArrayList<>();
        matchedUserIds.add(matchRequest.getUserId());
        matchedUserIds.add(matchedUsers.get(0).getId());
        matchResult.setMatchedUserIds(matchedUserIds);
        return matchResult;
    }

}
