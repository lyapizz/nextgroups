package com.lyapizz.cutshot.nextgroups.model;

import lombok.Value;

@Value
public class Team {

    String player1;
    String player2;
    Integer commonRating;
    Integer randomSeed;
    Integer level;
    String teamPage;

    @Override
    public String toString() {
        return player1 + " & " + player2 +"";
    }
}
