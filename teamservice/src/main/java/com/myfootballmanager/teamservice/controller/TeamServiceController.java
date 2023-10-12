package com.myfootballmanager.teamservice.controller;

import java.util.Map;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myfootballmanager.teamservice.model.Team;

@RestController
public class TeamServiceController {

  static ArrayList<Team> teams = new ArrayList<Team>() {{
    add(new Team(0, "Bastia"));
    add(new Team(1, "ACA GAULOIS"));
    add(new Team(2, "Monaco"));
  }};

  @GetMapping("/teams/{id}")
  public String get(@PathVariable Integer id) {
    Team team = teams.stream().filter(t -> t.id == id).findFirst().get();
    return team.toString();
  }

  @PostMapping("/teams")
  public String post(@RequestBody Map<String, String> body) {
    if (!body.containsKey("name")) {
      return "Error: the field <name> needs to be specified";
    }
    Team team = new Team(teams.size(), body.get("name"));
    teams.add(team);
    return team.toString();
  }

  @PutMapping("/teams/{id}")
  public String put(@PathVariable Integer id, @RequestBody Map<String, String> body) {
    if (!body.containsKey("name")) {
      return "Error: the field <name> needs to be specified";
    }
    Team team = teams.stream().filter(t -> t.id == id).findFirst().get();
    team.name = body.get("name");
    teams.set(id, team);
    return team.toString();
  }

  @DeleteMapping("/teams/{id}")
  public String delete(@PathVariable Integer id) {
    Team team = teams.stream().filter(t -> t.id == id).findFirst().get();
    Boolean status = teams.remove(team);
    return "Delete: " + status;
  }

}