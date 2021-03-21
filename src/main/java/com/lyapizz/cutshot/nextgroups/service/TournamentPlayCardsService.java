package com.lyapizz.cutshot.nextgroups.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lyapizz.cutshot.nextgroups.model.TournamentPlayCards;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class TournamentPlayCardsService {

    private final Pattern pattern = Pattern.compile(".*'(.*)'.*");

    public TournamentPlayCards extract(Element element) {
        List<String> playCardList = new ArrayList<>();

        for (Element teamElement : element.getElementsByClass("coach_tabel_new")) {
            if(isApproved(teamElement)) {
                String rawTeamLink = teamElement.attr("onclick");
                Matcher matcher = pattern.matcher(rawTeamLink);
                if (matcher.matches()) {
                    playCardList.add(matcher.group(1));
                }
            }
        }
        return new TournamentPlayCards(playCardList);
    }

    private boolean isApproved(Element teamElement) {
        for(Element child : teamElement.children()){
            if(child.hasClass("players_table_new_n_approve")){
                return child.text().equals("Подтверждено");
            }
        }
        return false;
    }

}
