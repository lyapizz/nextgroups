package com.lyapizz.cutshot.nextgroups.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lyapizz.cutshot.nextgroups.model.TournamentPlayCards;
import com.lyapizz.cutshot.nextgroups.model.TournamentPlayCards.TeamPage;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class TournamentPlayCardsService {

    private final Pattern pattern = Pattern.compile(".*'(.*)'.*");

    public TournamentPlayCards extract(Element element) {
        List<TeamPage> playCardList = new ArrayList<>();

        for (Element teamElement : element.getElementsByClass("coach_tabel_new")) {
            if (isApproved(teamElement)) {
                String rawTeamLink = teamElement.attr("onclick");
                Matcher matcher = pattern.matcher(rawTeamLink);
                if (matcher.matches()) {
                    playCardList.add(new TeamPage(matcher.group(1), getPlayer1(teamElement), getPlayer2(teamElement), getCommonRating(teamElement), getTeamLevel(teamElement)));
                }
            }
        }
        return new TournamentPlayCards(playCardList);
    }

    private String getPlayer1(Element teamElement) {
        return teamElement.getElementsByClass("players_table_new_person").get(0).text();
    }

    private String getPlayer2(Element teamElement) {
        Elements players = teamElement.getElementsByClass("players_table_new_person");
        if (players.size() == 2) {
            return players.get(1).text();
        } else {
            return "unknown";
        }
    }

    private int getCommonRating(Element teamElement) {
        return Integer.parseInt(teamElement.child(teamElement.childrenSize() - 2).text());
    }

    private int getTeamLevel(Element teamElement) {
        return Integer.parseInt(teamElement.child(teamElement.childrenSize() - 3).text());
    }

    private boolean isApproved(Element teamElement) {
        for (Element child : teamElement.children()) {
            if (child.hasClass("players_table_new_n_approve")) {
                return child.text().equals("Подтверждено") || child.text().equals("WILD CARD");
            }
        }
        return false;
    }

}
