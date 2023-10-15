package com.myfootballmanager.statsservice.model;

import io.swagger.annotations.ApiModelProperty;

public class PlayerStats {
  @ApiModelProperty(notes = "Id of the Player Stats", name="id", required=true, value="id")
  public Integer id;

  @ApiModelProperty(notes = "Number of goals by player", name="goals", required=true, value="goals")
  public Integer goals;

  @ApiModelProperty(notes = "Number of matches played", name="matchPlayed", required=true, value="matchPlayed")
  public Integer matchPlayed;

  @ApiModelProperty(notes = "Player name", name="playerName", required=true, value="playerName")
  public String playerName;

  public PlayerStats(Integer id, Integer goals, Integer matchPlayed, String playerName) {
    this.id = id;
    this.goals = goals;
    this.matchPlayed = matchPlayed;
    this.playerName = playerName;
  }

  @Override
  public String toString() {
    return "{\n\tid: " + this.id + ",\n\tPlayer name: " + this.playerName + ",\n\tGoals: " + this.goals + ",\n\tMatch Played: " + this.matchPlayed + "\n}";
  }
}