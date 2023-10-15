package com.myfootballmanager.playerservice.model;

import io.swagger.annotations.ApiModelProperty;

public class Player {
  @ApiModelProperty(notes = "Id of the Player", name="id", required=true, value="id")
  public Integer id;

  @ApiModelProperty(notes = "Name of the Player", name="name", required=true, value="name")
  public String name;

  @ApiModelProperty(notes = "Team name of the Player", name="team_name", required=true, value="team_name")
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
