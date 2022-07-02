package com.lyapizz.cutshot.nextgroups.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lyapizz.cutshot.nextgroups.NoRandomSeedException;
import com.lyapizz.cutshot.nextgroups.model.Team;
import com.lyapizz.cutshot.nextgroups.model.TournamentPlayCards;
import com.lyapizz.cutshot.nextgroups.model.TournamentPlayCards.TeamPage;
import com.lyapizz.cutshot.nextgroups.service.document.DocumentReader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class TeamsCreator {

    DocumentReader documentReader;

    private final Pattern SEED_PATTERN = Pattern.compile(".*Номер жеребъевки: (\\d+).*");

    public List<Team> createTeams(TournamentPlayCards tournamentPlayCards) {
        List<Team> result = Collections.synchronizedList(new ArrayList<>());
        long startTime = System.currentTimeMillis();
        tournamentPlayCards.getTeamsPages().parallelStream().forEach(teamPage -> createTeam(result, teamPage));

        log.info("Team creation took {} ms", System.currentTimeMillis() - startTime);

        return result;
    }

    private void createTeam(List<Team> result, TeamPage teamPage) {
        try {
            Team team = extractTeam(teamPage);
            result.add(team);
        } catch (IOException exception) {
            log.error("Error during creation of team: {}", teamPage, exception);
        }
    }

    private Team extractTeam(TeamPage teamPage) throws IOException {
        Document document = documentReader.read(teamPage.getUrl());
        List<String> players = findPlayers(document);
        Integer randomSeed = findRandomSeed(document);
        return new Team(players.get(0), players.get(1), teamPage.getCommonRating(), randomSeed, teamPage.getLevel(), teamPage.getUrl());
    }


    private List<String> findPlayers(Document document) {
        List<String> players = new ArrayList<>();
        for (Element playerElement : document.getElementsByClass("team_player_name")) {
            players.add(playerElement.text());
        }
        if (players.size() == 1) {
            players.add("unknown");
        }
        return players;
    }

    private Integer findRandomSeed(Document document) {
        for (Element teamCardElement : document.getElementsByClass("span_team_header")) {
            Matcher matcher = SEED_PATTERN.matcher(teamCardElement.text());
            if (matcher.matches()) {
                return Integer.parseInt(matcher.group(1));
            }
        }
        return 0;
    }

}
