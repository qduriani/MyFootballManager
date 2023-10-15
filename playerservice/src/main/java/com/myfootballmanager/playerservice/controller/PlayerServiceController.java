package com.myfootballmanager.playerservice.controller;

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

import com.myfootballmanager.playerservice.model.Player;

@RestController
public class PlayerServiceController {
  
  private static final CircuitBreaker circuitBreaker;
  
  static {
    CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
    .failureRateThreshold(50)
    .waitDurationInOpenState(Duration.ofMillis(1000))
    .slidingWindowSize(2)
    .build();
    
    circuitBreaker = CircuitBreaker.of("player-service", circuitBreakerConfig);
  }
  
  static ArrayList<Player> players = new ArrayList<Player>() {{
    add(new Player(0, "Benji", "Bastia"));
    add(new Player(1, "FPF", "Bastia"));
    add(new Player(2, "2J", "ACA GAULOIS"));
    add(new Player(3, "JBay", "Monaco"));
  }};
  
  // @GetMapping("/players/{id}")
  public String get(@PathVariable Integer id) {
    Player player = players.stream().filter(p -> p.id == id).findFirst().orElseThrow();
    return player.toString();
  }
  
  // @PostMapping("/players")
  public String post(@RequestBody Map<String, String> body) {
    if (!(body.containsKey("name") && body.containsKey("team_name"))) {
      throw new RuntimeException("Error: the fields <name> and <team_name> needs to be specified");
    }
    Player player = new Player(players.size(), body.get("name"), body.get("team_name"));
    players.add(player);
    return player.toString();
  }
  
  // @PutMapping("/players/{id}")
  public String put(@PathVariable Integer id, @RequestBody Map<String, String> body) {
    if (!(body.containsKey("name") && body.containsKey("team_name"))) {
      throw new RuntimeException("Error: the fields <name> and <team_name> needs to be specified");
    }
    Player player = players.stream().filter(p -> p.id == id).findFirst().orElseThrow();
    player.name = body.get("name");
    player.team_name = body.get("team_name");
    players.set(id, player);
    return player.toString();
  }
  
  // @DeleteMapping("/players/{id}")
  public String delete(@PathVariable Integer id) {
    Player player = players.stream().filter(p -> p.id == id).findFirst().orElseThrow();
    Boolean status = players.remove(player);
    return "Delete: " + status;
  }

  @PostMapping("/players")
  public String handle_post(@RequestBody Map<String, String> body) {
    return circuitBreaker.executeSupplier(() -> post(body));
  }
  
  @RequestMapping("/players/{id}")
  public String getplayer(HttpServletRequest request, @PathVariable Integer id, @RequestBody Map<String, String> body) {
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