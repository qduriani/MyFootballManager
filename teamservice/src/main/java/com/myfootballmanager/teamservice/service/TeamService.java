package com.myfootballmanager.teamservice.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.myfootballmanager.teamservice.model.Team;

@Service
public class TeamService {

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  private static ArrayList<Team> teams = new ArrayList<Team>() {{
    add(new Team(0, "Bastia"));
    add(new Team(1, "ACA GAULOIS"));
    add(new Team(2, "Monaco"));
  }};

  public Team getTeam(Integer id) {
    return teams.stream().filter(p -> p.id == id).findFirst().orElseThrow();
  }

  public Team postTeam(@RequestBody Map<String, String> body) {
    Team team = new Team(teams.size(), body.get("name"));
    teams.add(team);
    return team;
  }

  public Team putTeam(Integer id, @RequestBody Map<String, String> body) {
    Team team = teams.stream().filter(p -> p.id == id).findFirst().orElseThrow();
    team.name = body.get("name");
    teams.set(id, team);
    return team;
  }

  public Team deleteTeam(Integer id) {
    Team team = teams.stream().filter(p -> p.id == id).findFirst().orElseThrow();
    teams.remove(team);
    return team;
  }
}
