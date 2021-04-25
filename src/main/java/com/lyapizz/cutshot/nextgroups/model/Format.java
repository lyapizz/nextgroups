package com.lyapizz.cutshot.nextgroups.model;

public enum Format {
    W_SINGLE_4 (4),
    W_SINGLE_6 (6);

    Integer teamsInGroup;

    Format(Integer teamsInGroup) {
        this.teamsInGroup = teamsInGroup;
    }

    public Integer getTeamsInGroup() {
        return teamsInGroup;
    }
}
