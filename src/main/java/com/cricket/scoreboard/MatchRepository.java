package com.cricket.scoreboard;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface MatchRepository extends MongoRepository<Match, String> {}
