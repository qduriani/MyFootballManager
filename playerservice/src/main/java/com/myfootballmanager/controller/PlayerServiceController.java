package com.myfootballmanager.playerservice.controller;

import java.util.Map;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myfootballmanager.playerservice.model.Player;

@RestController
public class PlayerServiceController {

  static ArrayList<Player> players = new ArrayList<Player>() {{
    add(new Player(0, "Benji", "Bastia"));
    add(new Player(1, "FPF", "Bastia"));
    add(new Player(2, "2J", "ACA GAULOIS"));
    add(new Player(3, "JBay", "Monaco"));
  }};

  @GetMapping("/players/{id}")
  public String get(@PathVariable Integer id) {
    Player player = players.stream().filter(p -> p.id == id).findFirst().get();
    return player.toString();
  }

  @PostMapping("/players")
  public String post(@RequestBody Map<String, String> body) {
    if (!(body.containsKey("name") && body.containsKey(("team_name")))) {
      return "Error: the fields <name> and <team_name> needs to be specified";
    }
    Player player = new Player(players.size(), body.get("name"), body.get("team_name"));
    players.add(player);
    return player.toString();
  }

  @PutMapping("/players/{id}")
  public String put(@PathVariable Integer id, @RequestBody Map<String, String> body) {
    if (!(body.containsKey("name") && body.containsKey(("team_name")))) {
      return "Error: the fields <name> and <team_name> needs to be specified";
    }
    Player player = players.stream().filter(t -> t.id == id).findFirst().get();
    player.name = body.get("name");
    player.team_name = body.get("team_name");
    players.set(id, player);
    return player.toString();
  }

  @DeleteMapping("/players/{id}")
  public String delete(@PathVariable Integer id) {
    Player player = players.stream().filter(t -> t.id == id).findFirst().get();
    Boolean status = players.remove(player);
    return "Delete: " + status;
  }

}