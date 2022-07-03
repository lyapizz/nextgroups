package com.lyapizz.cutshot.nextgroups.service;

import static com.lyapizz.cutshot.nextgroups.model.Problem.QUOTA_IS_NOT_REACHED;
import static com.lyapizz.cutshot.nextgroups.model.Problem.RANDOM_SEED_IS_NOT_DEFINED;
import static java.util.Collections.emptyList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lyapizz.cutshot.nextgroups.model.Format;
import com.lyapizz.cutshot.nextgroups.model.Group;
import com.lyapizz.cutshot.nextgroups.model.GroupResult;
import com.lyapizz.cutshot.nextgroups.model.Team;
import com.lyapizz.cutshot.nextgroups.model.TournamentPlayCards;
import com.lyapizz.cutshot.nextgroups.model.response.GroupResultResponse;
import com.lyapizz.cutshot.nextgroups.utils.HttpUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

    public GroupResultResponse calculateGroups(String tournament, Format format) throws IOException {
        List<GroupResult> result = new ArrayList<>();

        Document doc = HttpUtils.readFromLink(tournament);
        LOG.info(doc.title());

        Map<Integer, Integer> quoteMap = quotaService.findQuotes(doc);
        Map<Integer, String> categoryMap = categoryService.findCategories(doc);
        Map<Integer, Element> tournamentMap = createTournamentMap(doc);

        tournamentMap.entrySet()
                .forEach(tournamentEntry -> {
                    Integer quota = quoteMap.get(tournamentEntry.getKey());
                    String category = categoryMap.get(tournamentEntry.getKey());

                    GroupResult groupResult = processOneTournament(format, quota, category, tournamentEntry);
                    if (groupResult != null) {
                        result.add(groupResult);
                    }
                });
        return new GroupResultResponse(doc.title(), result);
    }

    private Map<Integer, Element> createTournamentMap(Document doc) {
        Map<Integer, Element> tournamentMap = new HashMap<>();
        Elements tornamentElements = doc.getElementsByClass(TOURNAMENT_PLAYERS_TABLE_CLASS_NAME);
        for (int i = 0; i < tornamentElements.size(); i++) {
            Element tournament = tornamentElements.get(i);
            if (isPlannedTournament(tournament)) {
                tournamentMap.put(i, tournament);
            }
        }
        return tournamentMap;
    }

    private GroupResult processOneTournament(Format format, Integer quota, String category, Entry<Integer, Element> tournamentEntry) {
        // skip "tsar of the mountain" tournament
        if (category.contains("ЦАРЬ ГОРЫ")) {
            return null;
        }
        TournamentPlayCards playCards = tournamentPlayCardsService.extract(tournamentEntry.getValue());
        if (playCards.quotaIsReached(quota)) {
                List<Team> allTeams = teamsCreator.createTeams(playCards);
                LOG.info("Teams were created!");
                List<Group> groups = groupsCreator.createGroups(allTeams, format);
                LOG.info("Groups were created!");
                for (Group group : groups) {
                    LOG.info(group.toString());
                }
                return new GroupResult(category, groups, null);
        } else {
            return new GroupResult(category, emptyList(), QUOTA_IS_NOT_REACHED.getMessage());
        }
    }

    // filter tournaments with results
    private boolean isPlannedTournament(Element element) {
        return !element.getElementsByClass("players_table_new_n_approve").isEmpty();
    }

}
