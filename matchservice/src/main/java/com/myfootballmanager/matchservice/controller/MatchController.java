package com.myfootballmanager.matchservice.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.myfootballmanager.matchservice.service.MatchService;

@RestController
@Api(value = "MatchController", description = "REST API related to matches")
public class MatchController {

  private MatchService service;
  private static final CircuitBreaker circuitBreaker;

  @Autowired
  public MatchController(MatchService service) {
    this.service = service;
  }

  static {
    CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
    .failureRateThreshold(50)
    .waitDurationInOpenState(Duration.ofMillis(1000))
    .slidingWindowSize(2)
    .build();
    
    circuitBreaker = CircuitBreaker.of("player-service", circuitBreakerConfig);
  }

  @GetMapping("/matches/{id}")
  @ApiOperation(value = "Get match by ID", response = String.class, tags = "getMatch")
  public String get(@PathVariable Integer id) {
    try {
      return circuitBreaker.executeSupplier(() -> this.service.getMatch(id).toString());
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }

  @PostMapping("/matches")
  @ApiOperation(value = "Add match to the DB", response = String.class, tags = "postMatch")
  public String post(@RequestBody Map<String, String> body) {
    try {
      return circuitBreaker.executeSupplier(() -> this.service.postMatch(body).toString());
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }

  @PutMapping("/matches/{id}")
  @ApiOperation(value = "Modify match into the DB", response = String.class, tags = "putMatch")
  public String put(@PathVariable Integer id, @RequestBody Map<String, String> body) {
    try {
      return circuitBreaker.executeSupplier(() -> this.service.putMatch(id, body).toString());
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }

  @DeleteMapping("/matches/{id}")
  @ApiOperation(value = "Delete match into the DB", response = String.class, tags = "deleteMatch")
  public String delete(@PathVariable Integer id) {
    try {
      return circuitBreaker.executeSupplier(() -> this.service.deleteMatch(id).toString());
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }
  
  private String fallback(String message) {
    return message;
  }
}