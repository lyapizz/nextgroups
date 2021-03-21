package com.lyapizz.cutshot.nextgroups;

import java.io.IOException;
import java.util.List;

import com.lyapizz.cutshot.nextgroups.model.Group;
import com.lyapizz.cutshot.nextgroups.model.Team;
import com.lyapizz.cutshot.nextgroups.model.TournamentPlayCards;
import com.lyapizz.cutshot.nextgroups.service.GroupsCreator;
import com.lyapizz.cutshot.nextgroups.service.TeamsCreator;
import com.lyapizz.cutshot.nextgroups.service.TournamentPlayCardsService;
import com.lyapizz.cutshot.nextgroups.utils.HttpUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

    public static final String TOURNAMENT_PLAYERS_TABLE = "players_table_new";

    private final TournamentPlayCardsService tournamentPlayCardsService;

    private final TeamsCreator teamsCreator;

    private final GroupsCreator groupsCreator;

    public NextGroupsService(TournamentPlayCardsService tournamentPlayCardsService, TeamsCreator teamsCreator, GroupsCreator groupsCreator) {
        this.tournamentPlayCardsService = tournamentPlayCardsService;
        this.teamsCreator = teamsCreator;
        this.groupsCreator = groupsCreator;
    }

    public void printGroups() throws IOException {
        Document doc = HttpUtils.readFromLink(link);
        LOG.info(doc.title());

        for (Element tableElement : doc.getElementsByClass(TOURNAMENT_PLAYERS_TABLE)) {
            TournamentPlayCards playCards = tournamentPlayCardsService.extract(tableElement);
            if (playCards.containsPlayer(surnameToFind)) {
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
