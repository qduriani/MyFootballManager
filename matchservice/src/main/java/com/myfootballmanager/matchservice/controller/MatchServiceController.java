package com.myfootballmanager.matchservice.controller;

import java.util.Map;
import java.util.ArrayList;
import java.time.Duration;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HttpMethod;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;

import com.myfootballmanager.matchservice.model.Match;

@RestController
public class MatchServiceController {
  
  private static final CircuitBreaker circuitBreaker;
  
  static {
    CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
    .failureRateThreshold(50)
    .waitDurationInOpenState(Duration.ofMillis(1000))
    .slidingWindowSize(2)
    .build();
    
    circuitBreaker = CircuitBreaker.of("match-service", circuitBreakerConfig);
  }
  
  static ArrayList<Match> matches = new ArrayList<Match>() {{
    add(new Match(0, "9-0", "Bastia", "ACA"));
    add(new Match(1, "1-0", "PSG", "OM"));
    add(new Match(2, "0-0", "Arsenal", "MU"));
    add(new Match(3, "2-2", "Ghiso", "Corte"));
  }};
  
  // @GetMapping("/matches/{id}")
  public String get(@PathVariable Integer id) {
    Match match = matches.stream().filter(m -> m.id == id).findFirst().orElseThrow();
    return match.toString();
  }
  
  // @PostMapping("/matches")
  public String post(@RequestBody Map<String, String> body) {
    if (!(body.containsKey("score") && body.containsKey("team1_name") && body.containsKey("team2_name"))) {
      throw new RuntimeException("Error: the fields <score>, <team1_name> and <team2_name> needs to be specified");
    }
    Match match = new Match(matches.size(), body.get("score"), body.get("team1_name"), body.get("team2_name"));
    matches.add(match);
    return match.toString();
  }
  
  // @PutMapping("/matches/{id}")
  public String put(@PathVariable Integer id, @RequestBody Map<String, String> body) {
    if (!(body.containsKey("score") && body.containsKey("team1_name") && body.containsKey("team2_name"))) {
      throw new RuntimeException("Error: the fields <score>, <team1_name> and <team2_name> needs to be specified");
    }
    Match match = matches.stream().filter(m -> m.id == id).findFirst().orElseThrow();
    match.score = body.get("score");
    match.team1_name = body.get("team1_name");
    match.team2_name = body.get("team2_name");
    matches.set(id, match);
    return match.toString();
  }
  
  // @DeleteMapping("/matches/{id}")
  public String delete(@PathVariable Integer id) {
    Match match = matches.stream().filter(m -> m.id == id).findFirst().orElseThrow();
    Boolean status = matches.remove(match);
    return "Delete: " + status;
  }

  @PostMapping("/matches")
  public String handle_post(@RequestBody Map<String, String> body) {
    return circuitBreaker.executeSupplier(() -> post(body));
  }
  
  @RequestMapping("/matches/{id}")
  public String getMatch(HttpServletRequest request, @PathVariable Integer id, @RequestBody Map<String, String> body) {
    try {
      switch (request.getMethod()) {
      case HttpMethod.GET:
        return circuitBreaker.executeSupplier(() -> get(id));
      case HttpMethod.PUT:
        return circuitBreaker.executeSupplier(() -> put(id, body));
      case HttpMethod.DELETE:
        return circuitBreaker.executeSupplier(() -> delete(id));
      default:
        return fallback("Method not allowed");
      }
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }
  
  private String fallback(String message) {
    return message;
  }
  
}