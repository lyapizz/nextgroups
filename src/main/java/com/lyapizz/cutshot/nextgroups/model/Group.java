package com.lyapizz.cutshot.nextgroups.model;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;

@Value
public class Group {

    int id;
    List<Team> teams;

    @Override
    public String toString() {
        return "Group #" + id + ": \n" +
                teams.stream()
                        .map(Team::toString)
                        .collect(Collectors.joining("\n"));
    }
}
