package com.lyapizz.cutshot.nextgroups.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lyapizz.cutshot.nextgroups.model.Tournament;
import com.lyapizz.cutshot.nextgroups.model.response.CalenderResponse;
import com.lyapizz.cutshot.nextgroups.utils.HttpUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CalenderService {

    private static final String CALENDER_URL = "https://cut-shot.ru/";
    private static final String TOURNAMENT_TAG = "tournament";
    private static final String TOURNAMENT_INFO_TAG = "tournament-info";

    public CalenderResponse getCalender() throws IOException {
        List<Tournament> tournamentList = new ArrayList<>();

        Document doc = HttpUtils.readFromLink(CALENDER_URL);

        Elements tournaments = doc.getElementsByClass(TOURNAMENT_TAG);
        for (int i = 0; i < tournaments.size(); i++) {
            for(Element tournamentTag : tournaments.get(i).children()) {
                Element tournamentInfo = tournamentTag.getElementsByClass(TOURNAMENT_INFO_TAG).get(0);
                Tournament tournament = Tournament.builder()
                        .id(i)
                        .url(tournamentTag.attr("href"))
                        .name(tournamentInfo.getElementsByClass("tournament_title_home").get(0).text())
                        .categories(tournamentInfo.getElementsByClass("tournament_date_home").get(0).text())
                        .date(tournamentInfo.getElementsByClass("tournament_date2_home").get(0).text())
                        .build();
                tournamentList.add(tournament);
            }
        }
        return new CalenderResponse(tournamentList);
    }
}
