package com.lyapizz.cutshot.nextgroups;

import java.io.IOException;
import java.util.List;

import com.lyapizz.cutshot.nextgroups.model.Group;
import com.lyapizz.cutshot.nextgroups.model.Team;
import com.lyapizz.cutshot.nextgroups.model.TournamentPlayCards;
import com.lyapizz.cutshot.nextgroups.service.GroupsCreator;
import com.lyapizz.cutshot.nextgroups.service.QuotaService;
import com.lyapizz.cutshot.nextgroups.service.TeamsCreator;
import com.lyapizz.cutshot.nextgroups.service.TournamentPlayCardsService;
import com.lyapizz.cutshot.nextgroups.utils.HttpUtils;
import lombok.AllArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NextGroupsService {

    private static final Logger LOG = LoggerFactory.getLogger(NextGroupsService.class);

    @Value("${url}")
    private String link;

    @Value("${surname}")
    private String surnameToFind;

    private static final String TOURNAMENT_PLAYERS_TABLE_CLASS_NAME = "players_table_new";

    private final TournamentPlayCardsService tournamentPlayCardsService;

    private final TeamsCreator teamsCreator;

    private final GroupsCreator groupsCreator;

    private final QuotaService quotaService;

    public NextGroupsService(TournamentPlayCardsService tournamentPlayCardsService, TeamsCreator teamsCreator, GroupsCreator groupsCreator, QuotaService quotaService) {
        this.tournamentPlayCardsService = tournamentPlayCardsService;
        this.teamsCreator = teamsCreator;
        this.groupsCreator = groupsCreator;
        this.quotaService = quotaService;
    }

    public void printGroups() throws IOException {
        Document doc = HttpUtils.readFromLink(link);
        LOG.info(doc.title());

        List<Integer> quotes = quotaService.findQuotes(doc);
        Elements tournaments = doc.getElementsByClass(TOURNAMENT_PLAYERS_TABLE_CLASS_NAME);
        for (int i = 0; i < tournaments.size(); i++) {
            TournamentPlayCards playCards = tournamentPlayCardsService.extract(tournaments.get(i));
            if (playCards.containsPlayer(surnameToFind) && playCards.quotaIsReached(quotes.get(i))) {
                LOG.info("Tournament with you was found!");
                List<Team> allTeams = teamsCreator.createTeams(playCards);
                LOG.info("Teams were created!");
                List<Group> groups = groupsCreator.createGroups(allTeams);
                LOG.info("Groups were created!");
                for (Group group : groups) {
                    LOG.info(group.toString());
                }
            }
        }
    }

}
