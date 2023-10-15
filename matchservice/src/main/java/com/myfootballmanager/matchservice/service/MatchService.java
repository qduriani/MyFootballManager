package com.myfootballmanager.matchservice.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.myfootballmanager.matchservice.model.Match;

@Service
public class MatchService {

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  private static ArrayList<Match> matches = new ArrayList<Match>() {{
    add(new Match(0, "9-0", "Bastia", "ACA"));
    add(new Match(1, "1-0", "PSG", "OM"));
    add(new Match(2, "0-0", "Arsenal", "MU"));
    add(new Match(3, "2-2", "Ghiso", "Corte"));
  }};

  public Match getMatch(Integer id) {
    return matches.stream().filter(m -> m.id == id).findFirst().orElseThrow();
  }

  public Match postMatch(@RequestBody Map<String, String> body) {
    Match match = new Match(matches.size(), body.get("score"), body.get("team1_name"), body.get("team2_name"));
    matches.add(match);
    return match;
  }

  public Match putMatch(Integer id, @RequestBody Map<String, String> body) {
    Match match = matches.stream().filter(m -> m.id == id).findFirst().orElseThrow();
    match.score = body.get("score");
    match.team1_name = body.get("team1_name");
    match.team2_name = body.get("team2_name");
    matches.set(id, match);
    return match;
  }

  public Match deleteMatch(Integer id) {
    Match match = matches.stream().filter(m -> m.id == id).findFirst().orElseThrow();
    matches.remove(match);
    return match;
  }
}
