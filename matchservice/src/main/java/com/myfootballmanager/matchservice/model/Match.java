package com.myfootballmanager.matchservice.model;

import io.swagger.annotations.ApiModelProperty;

public class Match {
  @ApiModelProperty(notes = "Id of the Match", name="id", required=true, value="id")
  public Integer id;

  @ApiModelProperty(notes = "Score of the Match", name="score", required=true, value="score")
  public String score;

  @ApiModelProperty(notes = "Team name (Home)", name="team1_name", required=true, value="team1_name")
  public String team1_name;

  @ApiModelProperty(notes = "Team name (Out)", name="team2_name", required=true, value="team2_name")
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
