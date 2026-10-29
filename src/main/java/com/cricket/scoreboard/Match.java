package com.cricket.scoreboard;

import java.time.Instant;
import java.util.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("matches")
public class Match {
  @Id public String id;
  public String title, venue, status, battingTeam, bowlingTeam;
  public int battingRuns, wickets, balls, oversLimit;
  public List<String> recent = new ArrayList<>();
  public Map<String, Player> players = new LinkedHashMap<>();
  public Instant updatedAt = Instant.now();
  public Match() {}
  public Match(String id, String title, String venue, String battingTeam, String bowlingTeam) {
    this.id=id; this.title=title; this.venue=venue; this.battingTeam=battingTeam; this.bowlingTeam=bowlingTeam; this.status="LIVE"; this.oversLimit=20;
    players.put("A Sharma", new Player("A Sharma", 42, 27, 3)); players.put("R Singh", new Player("R Singh", 28, 19, 2)); players.put("M Khan", new Player("M Khan", 0, 0, 1));
  }
  public record Player(String name, int runs, int balls, int wickets) {}
}
