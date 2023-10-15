package com.myfootballmanager.teamservice.controller;

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

import com.myfootballmanager.teamservice.model.Team;

@RestController
public class TeamServiceController {
  
  private static final CircuitBreaker circuitBreaker;
  
  static {
    CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
    .failureRateThreshold(50)
    .waitDurationInOpenState(Duration.ofMillis(1000))
    .slidingWindowSize(2)
    .build();
    
    circuitBreaker = CircuitBreaker.of("team-service", circuitBreakerConfig);
  }
  
  static ArrayList<Team> teams = new ArrayList<Team>() {{
    add(new Team(0, "Bastia"));
    add(new Team(1, "ACA GAULOIS"));
    add(new Team(2, "Monaco"));
  }};
  
  // @GetMapping("/teams/{id}")
  public String get(@PathVariable Integer id) {
    Team team = teams.stream().filter(p -> p.id == id).findFirst().orElseThrow();
    return team.toString();
  }
  
  // @PostMapping("/teams")
  public String post(@RequestBody Map<String, String> body) {
    if (!(body.containsKey("name"))) {
      throw new RuntimeException("Error: the field <name> needs to be specified");
    }
    Team team = new Team(teams.size(), body.get("name"));
    teams.add(team);
    return team.toString();
  }
  
  // @PutMapping("/teams/{id}")
  public String put(@PathVariable Integer id, @RequestBody Map<String, String> body) {
    if (!(body.containsKey("name"))) {
      throw new RuntimeException("Error: the field <name> needs to be specified");
    }
    Team team = teams.stream().filter(p -> p.id == id).findFirst().orElseThrow();
    team.name = body.get("name");
    teams.set(id, team);
    return team.toString();
  }
  
  // @DeleteMapping("/teams/{id}")
  public String delete(@PathVariable Integer id) {
    Team team = teams.stream().filter(p -> p.id == id).findFirst().orElseThrow();
    Boolean status = teams.remove(team);
    return "Delete: " + status;
  }

  @PostMapping("/teams")
  public String handle_post(@RequestBody Map<String, String> body) {
    return circuitBreaker.executeSupplier(() -> post(body));
  }
  
  @RequestMapping("/teams/{id}")
  public String getteam(HttpServletRequest request, @PathVariable Integer id, @RequestBody Map<String, String> body) {
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