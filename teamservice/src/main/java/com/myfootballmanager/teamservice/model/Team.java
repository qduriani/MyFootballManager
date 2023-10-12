package com.myfootballmanager.teamservice.model;

public class Team {
  public Integer id;
  public String name;

  public Team(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString() {
    return "{\n\tid: " + this.id + ",\n\tname: " + this.name + "\n}";
  }
}
