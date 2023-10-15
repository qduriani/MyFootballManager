package com.myfootballmanager.statsservice.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.swagger.annotations.Api;

import com.myfootballmanager.statsservice.service.StatsService;

@RestController
@Api(value = "StatsController", description = "REST API related to stats")
public class StatsController {
  
  private StatsService service;
  private static final CircuitBreaker circuitBreaker;

  @Autowired
  public StatsController(StatsService service) {
    this.service = service;
  }

  static {
    CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
    .failureRateThreshold(50)
    .waitDurationInOpenState(Duration.ofMillis(1000))
    .slidingWindowSize(2)
    .build();
    
    circuitBreaker = CircuitBreaker.of("stats-service", circuitBreakerConfig);
  }

  @GetMapping("/team-stats/{teamId}")
  String teamStats(@PathVariable Integer teamId) {
    try {
      return circuitBreaker.executeSupplier(() -> this.service.getTeamStats(teamId).toString());
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }

  @GetMapping("/player-stats/{playerId}")
  String playerStats(@PathVariable Integer playerId) {
    try {
      return circuitBreaker.executeSupplier(() -> this.service.getPlayerStats(playerId).toString());
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }

  private String fallback(String message) {
    return message;
  }
}
