package com.myfootballmanager.matchservice.model;

public class Match {
  public Integer id;
  public String score;
  public String team1_name;
  public String team2_name;
  
  public Match(Integer id, String score, String team1_name, String team2_name) {
    this.id = id;
    this.score = score;
    this.team1_name = team1_name;
    this.team2_name = team2_name;
  }

  @Override
  public String toString() {
    return "{\n\tid: " + this.id + ",\n\tScore: " + this.score + ",\n\tTeam 1: " + this.team1_name + ",\n\tTeam 2: " + this.team2_name + "\n}";
  }
}
