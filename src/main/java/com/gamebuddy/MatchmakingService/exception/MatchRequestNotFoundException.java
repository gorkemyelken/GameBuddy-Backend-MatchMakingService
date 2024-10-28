package com.gamebuddy.MatchmakingService.exception;

public class MatchRequestNotFoundException extends RuntimeException {
    public MatchRequestNotFoundException(String message) {
        super(message);
    }
}