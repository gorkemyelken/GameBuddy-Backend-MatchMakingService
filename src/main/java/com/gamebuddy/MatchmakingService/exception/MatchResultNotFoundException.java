package com.gamebuddy.MatchmakingService.exception;

public class MatchResultNotFoundException extends RuntimeException {
    public MatchResultNotFoundException(String message) {
        super(message);
    }
}
