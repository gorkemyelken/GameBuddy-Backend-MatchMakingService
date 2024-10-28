package com.gamebuddy.MatchmakingService.controller;

import com.gamebuddy.MatchmakingService.dto.MatchRequestCreateDTO;
import com.gamebuddy.MatchmakingService.dto.MatchResultViewDTO;
import com.gamebuddy.MatchmakingService.service.MatchMakingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/match-making")
@CrossOrigin
public class MatchMakingController {

    private final MatchMakingService matchMakingService;

    @Autowired
    public MatchMakingController(MatchMakingService matchMakingService) {
        this.matchMakingService = matchMakingService;
    }

    @Operation(summary = "Find a new match request",
            description = "Finds a new match returns the match result details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Match request created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<MatchResultViewDTO> findMatch(@RequestBody MatchRequestCreateDTO matchRequestCreateDTO) {
        MatchResultViewDTO matchResult = matchMakingService.findMatch(matchRequestCreateDTO);
        return new ResponseEntity<>(matchResult, HttpStatus.CREATED);
    }

}
