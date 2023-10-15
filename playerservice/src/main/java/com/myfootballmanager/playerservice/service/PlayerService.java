package com.myfootballmanager.playerservice.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.myfootballmanager.playerservice.model.Player;

@Service
public class PlayerService {

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  private static ArrayList<Player> players = new ArrayList<Player>() {{
    add(new Player(0, "Benji", "Bastia"));
    add(new Player(1, "FPF", "Bastia"));
    add(new Player(2, "2J", "ACA GAULOIS"));
    add(new Player(3, "JBay", "Monaco"));
  }};

  public Player getPlayer(Integer id) {
    return players.stream().filter(p -> p.id == id).findFirst().orElseThrow();
  }

  public Player postPlayer(@RequestBody Map<String, String> body) {
    Player player = new Player(players.size(), body.get("name"), body.get("team_name"));
    players.add(player);
    return player;
  }

  public Player putPlayer(Integer id, @RequestBody Map<String, String> body) {
    Player player = players.stream().filter(p -> p.id == id).findFirst().orElseThrow();
    player.name = body.get("name");
    player.team_name = body.get("team_name");
    players.set(id, player);
    return player;
  }

  public Player deletePlayer(Integer id) {
    Player player = players.stream().filter(p -> p.id == id).findFirst().orElseThrow();
    players.remove(player);
    return player;
  }
}
