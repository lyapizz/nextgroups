package com.lyapizz.cutshot.nextgroups;

import static com.lyapizz.cutshot.nextgroups.model.Problem.QUOTA_IS_NOT_REACHED;
import static java.util.Collections.emptyList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lyapizz.cutshot.nextgroups.model.Format;
import com.lyapizz.cutshot.nextgroups.model.Group;
import com.lyapizz.cutshot.nextgroups.model.GroupResult;
import com.lyapizz.cutshot.nextgroups.model.Team;
import com.lyapizz.cutshot.nextgroups.model.TournamentPlayCards;
import com.lyapizz.cutshot.nextgroups.service.CategoryService;
import com.lyapizz.cutshot.nextgroups.service.GroupsCreator;
import com.lyapizz.cutshot.nextgroups.service.QuotaService;
import com.lyapizz.cutshot.nextgroups.service.TeamsCreator;
import com.lyapizz.cutshot.nextgroups.service.TournamentPlayCardsService;
import com.lyapizz.cutshot.nextgroups.utils.HttpUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NextGroupsService {

    private static final Logger LOG = LoggerFactory.getLogger(NextGroupsService.class);

    private static final String TOURNAMENT_PLAYERS_TABLE_CLASS_NAME = "players_table_new";

    private final TournamentPlayCardsService tournamentPlayCardsService;

    private final TeamsCreator teamsCreator;

    private final GroupsCreator groupsCreator;

    private final QuotaService quotaService;

    private final CategoryService categoryService;

    public NextGroupsService(TournamentPlayCardsService tournamentPlayCardsService, TeamsCreator teamsCreator, GroupsCreator groupsCreator, QuotaService quotaService,
                             CategoryService categoryService) {
        this.tournamentPlayCardsService = tournamentPlayCardsService;
        this.teamsCreator = teamsCreator;
        this.groupsCreator = groupsCreator;
        this.quotaService = quotaService;
        this.categoryService = categoryService;
    }

    public List<GroupResult> calculateGroups(String tournament, String surname, Format format) throws IOException {
        List<GroupResult> result = new ArrayList<>();

        Document doc = HttpUtils.readFromLink(tournament);
        LOG.info(doc.title());

        List<Integer> quotes = quotaService.findQuotes(doc);
        List<String> categories = categoryService.findCategories(doc);

        Elements tournaments = doc.getElementsByClass(TOURNAMENT_PLAYERS_TABLE_CLASS_NAME);
        for (int i = 0; i < tournaments.size(); i++) {
            TournamentPlayCards playCards = tournamentPlayCardsService.extract(tournaments.get(i));
            if (playCards.containsPlayer(surname)) {
                if (playCards.quotaIsReached(quotes.get(i))) {
                    LOG.info("Tournament with you was found!");
                    List<Team> allTeams = teamsCreator.createTeams(playCards);
                    LOG.info("Teams were created!");
                    List<Group> groups = groupsCreator.createGroups(allTeams, format);
                    LOG.info("Groups were created!");
                    result.add(new GroupResult(categories.get(i), groups, null));
                    for (Group group : groups) {
                        LOG.info(group.toString());
                    }
                } else {
                    result.add(new GroupResult(categories.get(i), emptyList(), QUOTA_IS_NOT_REACHED.getMessage()));
                }
            }
        }
        return result;
    }

}
