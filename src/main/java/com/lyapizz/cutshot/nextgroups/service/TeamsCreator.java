package com.lyapizz.cutshot.nextgroups.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        List<Team> result = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        boolean isRandomSeedNeeded = tournamentPlayCards.randomSeedIsNeeded();
        tournamentPlayCards.getTeamsPages().parallelStream().forEach(teamPage -> createTeam(result, teamPage, isRandomSeedNeeded));

        log.info("Team creation took {} ms", System.currentTimeMillis() - startTime);

        return result;
    }

    private void createTeam(List<Team> result, TeamPage teamPage, boolean isRandomSeedNeeded) {
        try {
            Team team = extractTeam(teamPage, isRandomSeedNeeded);
            result.add(team);
        } catch (IOException exception) {
            log.error("Error during creation of team: {}", teamPage, exception);
        }
    }

    private Team extractTeam(TeamPage teamPage, boolean isRandomSeedNeeded) throws IOException {
        Integer randomSeed = 0;
        if(isRandomSeedNeeded) {
            Document document = documentReader.read(teamPage.getUrl());
            randomSeed = findRandomSeed(document);
        }
        return new Team(teamPage.getPlayer1(), teamPage.getPlayer2(), teamPage.getCommonRating(), randomSeed, teamPage.getLevel(), teamPage.getUrl());
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
