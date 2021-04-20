package com.lyapizz.cutshot.nextgroups.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;

@Value
public class Team {

    String player1;
    String player2;
    @JsonIgnore
    Integer commonRating;
    @JsonIgnore
    Integer randomSeed;
    @JsonIgnore
    String teamPage;

    @Override
    public String toString() {
        return player1 + " & " + player2 +"";
    }
}
