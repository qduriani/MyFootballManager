package com.myfootballmanager.statsservice.model;

import io.swagger.annotations.ApiModelProperty;

public class TeamStats {
  @ApiModelProperty(notes = "Id of the Team stats", name="id", required=true, value="id")
  public Integer id;

  @ApiModelProperty(notes = "Rank of the team", name="rank", required=true, value="rank")
  public Integer rank;

  @ApiModelProperty(notes = "Number of total goals", name="goals", required=true, value="goals")
  public Integer goals;

  @ApiModelProperty(notes = "Team name", name="teamName", required=true, value="teamName")
  public String teamName;

  public TeamStats(Integer id, Integer rank, Integer goals, String teamName) {
    this.id = id;
    this.rank = rank;
    this.goals = goals;
    this.teamName = teamName;
  }

  @Override
  public String toString() {
    return "{\n\tid: " + this.id + ",\n\tTeam name: " + this.teamName + ",\n\tGoals: " + this.goals + ",\n\tRank: " + this.rank + "\n}";
  }
}