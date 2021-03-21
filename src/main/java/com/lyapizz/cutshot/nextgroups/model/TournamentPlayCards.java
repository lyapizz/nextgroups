package com.lyapizz.cutshot.nextgroups.model;

import java.util.List;

import lombok.Value;

@Value
public class TournamentPlayCards {

    List<String> teamsPages;

    public boolean containsPlayer(String surname){
        return teamsPages.stream().anyMatch( s -> s.contains(surname));
    }

}
