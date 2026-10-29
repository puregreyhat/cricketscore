package com.cricket.scoreboard;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController @CrossOrigin(origins = "*") @RequestMapping("/api/matches")
public class MatchController {
  private final MatchService service; public MatchController(MatchService service){this.service=service;}
  @GetMapping public Iterable<Match> all(){return service.all();}
  @GetMapping("/{id}") public Match one(@PathVariable String id){return service.one(id);}
  @PostMapping("/{id}/events") public Match score(@PathVariable String id,@Valid @RequestBody ScoreEvent event){return service.score(id,event);}
  @GetMapping(value="/{id}/stream", produces=MediaType.TEXT_EVENT_STREAM_VALUE) public SseEmitter stream(@PathVariable String id){service.one(id); return service.stream();}
}
