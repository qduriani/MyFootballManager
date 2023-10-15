package com.myfootballmanager.playerservice.model;

public class Player {
  public Integer id;
  public String name;
  public String team_name;

  public Player(Integer id, String name, String team_name) {
    this.id = id;
    this.name = name;
    this.team_name = team_name;
  }

  @Override
  public String toString() {
    return "{\n\tid: " + this.id + ",\n\tname: " + this.name + ",\n\tteam_name: " + this.team_name + "\n}";
  }
}
