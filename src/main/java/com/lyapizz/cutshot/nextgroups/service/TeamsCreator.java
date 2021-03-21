package com.lyapizz.cutshot.nextgroups.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lyapizz.cutshot.nextgroups.model.Team;
import com.lyapizz.cutshot.nextgroups.model.TournamentPlayCards;
import com.lyapizz.cutshot.nextgroups.service.document.DocumentReader;
import com.lyapizz.cutshot.nextgroups.utils.HttpUtils;
import lombok.AllArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TeamsCreator {

    DocumentReader documentReader;

    public List<Team> createTeams(TournamentPlayCards tournamentPlayCards) throws IOException {
        List<Team> result = new ArrayList<>();
        for (String teamPage : tournamentPlayCards.getTeamsPages()) {
            Team team = extractTeam(teamPage);
            result.add(team);
        }

        return result;
    }

    private Team extractTeam(String teamPage) throws IOException {
        Document document = documentReader.read(teamPage);
        List<String> players = findPlayers(document);
        Integer commonRating = findCommonRating(document);
        Integer randomSeed = findRandomSeed(document);

        return new Team(players.get(0), players.get(1), commonRating, randomSeed);
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

    private Integer findCommonRating(Document document) {
        for (Element playerElement : document.getElementsByClass("team_zayavka_cont_block2")) {
            if(!playerElement.getElementsContainingText("Рейтинг").isEmpty()){
                for(Element child: playerElement.getElementsByClass("team_zayavka_cont_medal2")){
                    return Integer.parseInt(child.text());
                }
            }
        }
        return null;
    }
    private Integer findRandomSeed(Document document) {
        for (Element playerElement : document.getElementsByClass("team_zayavka_cont_block2")) {
            if(!playerElement.getElementsContainingText("Жеребъевка").isEmpty()){
                for(Element child: playerElement.getElementsByClass("team_zayavka_cont_medal2")){
                    return Integer.parseInt(child.text());
                }
            }
        }
        return null;
    }

}
