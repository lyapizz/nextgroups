package com.lyapizz.cutshot.nextgroups.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.lyapizz.cutshot.nextgroups.model.Team;
import com.lyapizz.cutshot.nextgroups.model.TournamentPlayCards;
import com.lyapizz.cutshot.nextgroups.service.document.DocumentReader;
import com.lyapizz.cutshot.nextgroups.service.document.FileDocumentReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamsCreatorTest {

    TeamsCreator teamsCreator;

    @BeforeEach
    void setUp() {
        DocumentReader documentReader = new FileDocumentReader();
        teamsCreator = new TeamsCreator(documentReader);
    }

    @Test
    void createTeams_oneTeam_twoPlayers() throws Exception {
        List<String> teamPages = List.of("/lepekhin-golosov.html");
        TournamentPlayCards tournamentPlayCards = new TournamentPlayCards(teamPages);

        List<Team> teamList = teamsCreator.createTeams(tournamentPlayCards);

        assertEquals(1, teamList.size());
        Team team = teamList.get(0);
        assertEquals("Лепехин Павел", team.getPlayer1());
        assertEquals("Голосов Михаил", team.getPlayer2());
        assertEquals(675, team.getCommonRating());
        assertEquals(816, team.getRandomSeed());
    }

    @Test
    void createTeams_oneTeam_SecondPlayerIsNull() throws Exception {
        List<String> teamPages = List.of("/berezovskij.html");
        TournamentPlayCards tournamentPlayCards = new TournamentPlayCards(teamPages);

        List<Team> teamList = teamsCreator.createTeams(tournamentPlayCards);

        assertEquals(1, teamList.size());
        Team team = teamList.get(0);
        assertEquals("Березовский Дмитрий", team.getPlayer1());
        assertEquals("unknown", team.getPlayer2());
        assertEquals(105, team.getCommonRating());
        assertEquals(1166, team.getRandomSeed());
    }
}