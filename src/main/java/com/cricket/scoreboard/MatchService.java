package com.cricket.scoreboard;

import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class MatchService {
  private final MatchRepository repo; private final CopyOnWriteArrayList<SseEmitter> clients = new CopyOnWriteArrayList<>();
  public MatchService(MatchRepository repo) { this.repo=repo; }
  public Iterable<Match> all() { if(repo.count()==0) repo.save(new Match("match-1","India vs Australia","Mumbai • Wankhede Stadium","India","Australia")); return repo.findAll(); }
  public Match one(String id) { return repo.findById(id).orElseThrow(); }
  public SseEmitter stream() { var e=new SseEmitter(0L); clients.add(e); e.onCompletion(()->clients.remove(e)); e.onTimeout(()->clients.remove(e)); return e; }
  public Match score(String id, ScoreEvent event) {
    Match m=one(id); m.battingRuns+=event.runs(); m.balls++; if(event.wicket()) m.wickets++; m.recent.add(0, event.wicket()?"W":String.valueOf(event.runs())); if(m.recent.size()>8)m.recent.remove(8); m.updatedAt=Instant.now(); var saved=repo.save(m);
    clients.removeIf(e->{try { e.send(SseEmitter.event().name("score").data(saved)); return false; } catch(Exception x){return true;}}); return saved;
  }
}
