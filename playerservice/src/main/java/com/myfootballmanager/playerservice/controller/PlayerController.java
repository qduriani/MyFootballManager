package com.myfootballmanager.playerservice.controller;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;

import com.myfootballmanager.playerservice.service.PlayerService;

@RestController
@Api(value = "PlayerController", description = "REST API related to players")
public class PlayerController {

  private PlayerService service;
  private static final CircuitBreaker circuitBreaker;

  @Autowired
  public PlayerController(PlayerService service) {
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
  
  @GetMapping("/players/{id}")
  @ApiOperation(value = "Get player by ID", response = String.class, tags = "getPlayer")
  public String get(@PathVariable Integer id) {
    try {
      return circuitBreaker.executeSupplier(() -> this.service.getPlayer(id).toString());
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }
  

  @PostMapping("/players")
  @ApiOperation(value = "Add a player", response = String.class, tags = "postPlayer")
  public String post(@RequestBody Map<String, String> body) {
    try {
      return circuitBreaker.executeSupplier(() -> this.service.postPlayer(body).toString());
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }
  

  @PutMapping("/players/{id}")
  @ApiOperation(value = "Modify a player", response = String.class, tags = "putPlayer")
  public String put(@PathVariable Integer id, @RequestBody Map<String, String> body) {
    try {
      return circuitBreaker.executeSupplier(() -> this.service.putPlayer(id, body).toString());
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }
  

  @DeleteMapping("/players/{id}")
  @ApiOperation(value = "Delete a player", response = String.class, tags = "deletePlayer")
  public String delete(@PathVariable Integer id) {
    try {
      return circuitBreaker.executeSupplier(() -> this.service.deletePlayer(id).toString());
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }
  
  private String fallback(String message) {
    return message;
  }
}