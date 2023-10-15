package com.myfootballmanager.teamservice.controller;

import java.util.Map;
import java.time.Duration;

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

import com.myfootballmanager.teamservice.service.TeamService;

@RestController
@Api(value = "TeamController", description = "REST API related to teams")
public class TeamController {
  
  private TeamService service;
  private static final CircuitBreaker circuitBreaker;

  @Autowired
  public TeamController(TeamService service) {
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
  
  @GetMapping("/teams/{id}")
  @ApiOperation(value = "Get a team by ID", response = String.class, tags = "getTeam")
  public String get(@PathVariable Integer id) {
    try {
      return circuitBreaker.executeSupplier(() -> this.service.getTeam(id).toString());
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }
  

  @PostMapping("/teams")
  @ApiOperation(value = "Add a team", response = String.class, tags = "postTeam")
  public String post(@RequestBody Map<String, String> body) {
     try {
      return circuitBreaker.executeSupplier(() -> this.service.postTeam(body).toString());
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }
  
  @PutMapping("/teams/{id}")
  @ApiOperation(value = "Modify a team", response = String.class, tags = "putTeam")
  public String put(@PathVariable Integer id, @RequestBody Map<String, String> body) {
     try {
      return circuitBreaker.executeSupplier(() -> this.service.putTeam(id, body).toString());
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }
  
  @DeleteMapping("/teams/{id}")
  @ApiOperation(value = "Delete a team", response = String.class, tags = "deleteTeam")
  public String delete(@PathVariable Integer id) {
    try {
      return circuitBreaker.executeSupplier(() -> this.service.deleteTeam(id).toString());
    } catch (Exception e) {
      return fallback(e.getMessage());
    }
  }
  
  private String fallback(String message) {
    return message;
  }
}