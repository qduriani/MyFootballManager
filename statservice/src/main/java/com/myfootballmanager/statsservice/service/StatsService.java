package com.myfootballmanager.statsservice.service;

import java.util.ArrayList;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myfootballmanager.statsservice.model.TeamStats;
import com.myfootballmanager.statsservice.model.PlayerStats;

@Service
public class StatsService {

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  private static ArrayList<TeamStats> teamStats = new ArrayList<TeamStats>() {{
    add(new TeamStats(0, 1, 100, "qdus"));
    add(new TeamStats(1, 10, 20, "Ghiso"));
    add(new TeamStats(2, 2, 29, "PV"));
    add(new TeamStats(3, -1, 1000, "Barça"));
  }};
  
  private static ArrayList<PlayerStats> playerStats = new ArrayList<PlayerStats>() {{
    add(new PlayerStats(0, 22, 10, "qdu"));
    add(new PlayerStats(1, 0, 20, "FPF"));
    add(new PlayerStats(2, 2, 0, "2J"));
    add(new PlayerStats(3, 1217, 2, "Messi"));
  }};

  public TeamStats getTeamStats(Integer id) {
    return teamStats.stream().filter(t -> t.id == id).findFirst().orElseThrow();
  }

  public PlayerStats getPlayerStats(Integer id) {
    return playerStats.stream().filter(p -> p.id == id).findFirst().orElseThrow();
  }
}
