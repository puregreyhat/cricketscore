package com.cricket.scoreboard;
import jakarta.validation.constraints.Min;
public record ScoreEvent(@Min(0) int runs, boolean wicket, String batter) {}
