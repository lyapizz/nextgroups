package com.lyapizz.cutshot.nextgroups.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import com.lyapizz.cutshot.nextgroups.NoRandomSeedException;
import com.lyapizz.cutshot.nextgroups.model.Team;
import com.lyapizz.cutshot.nextgroups.model.TournamentPlayCards;
import com.lyapizz.cutshot.nextgroups.model.TournamentPlayCards.TeamPage;
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
    void createTeams_oneTeam_twoPlayers() {
        List<TeamPage> teamPages = List.of(new TeamPage("/lepekhin-golosov.html", 675));
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
    void createTeams_manyTeams_parallelStreamWorks() {
        List<TeamPage> teamPages = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            teamPages.add(new TeamPage("/lepekhin-golosov.html", 675));
        }
        TournamentPlayCards tournamentPlayCards = new TournamentPlayCards(teamPages);

        List<Team> teamList = teamsCreator.createTeams(tournamentPlayCards);

        assertEquals(100, teamList.size());
    }

    @Test
    void createTeams_oneTeam_SecondPlayerIsNull() {
        List<TeamPage> teamPages = List.of(new TeamPage("/berezovskij.html", 105));
        TournamentPlayCards tournamentPlayCards = new TournamentPlayCards(teamPages);

        List<Team> teamList = teamsCreator.createTeams(tournamentPlayCards);

        assertEquals(1, teamList.size());
        Team team = teamList.get(0);
        assertEquals("Березовский Дмитрий", team.getPlayer1());
        assertEquals("unknown", team.getPlayer2());
        assertEquals(105, team.getCommonRating());
        assertEquals(1166, team.getRandomSeed());
    }

    @Test
    void createTeams_noRandomSeed_throwException() {
        List<TeamPage> teamPages = List.of(new TeamPage("/no_random_seed.html", 777));
        TournamentPlayCards tournamentPlayCards = new TournamentPlayCards(teamPages);

        assertThrows(NoRandomSeedException.class, () -> teamsCreator.createTeams(tournamentPlayCards));
    }
}