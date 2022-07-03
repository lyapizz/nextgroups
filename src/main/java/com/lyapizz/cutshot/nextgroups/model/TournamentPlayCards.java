package com.lyapizz.cutshot.nextgroups.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Value
public class TournamentPlayCards {

    private static final Logger LOG = LoggerFactory.getLogger(TournamentPlayCards.class);

    List<TeamPage> teamsPages;

    public boolean quotaIsReached(Integer quota) {
        boolean isReached = quota == null || teamsPages.size() == quota;
        if (!isReached) {
            LOG.info("Quota " + quota + " for this tournament is not fulfilled!");
        }
        return isReached;
    }

    public boolean randomSeedIsNeeded() {
        Set<Integer> distinctRatings = teamsPages.stream()
                .map(teamPage -> teamPage.commonRating)
                .collect(Collectors.toSet());
        if (teamsPages.size() > distinctRatings.size()) {
            LOG.info("Random seed will be used for this tournament. Example team: {}", teamsPages.get(0).getUrl());
            return true;
        }
        return false;
    }

    @Value
    public static class TeamPage {
        String url;
        String player1;
        String player2;
        Integer commonRating;
        Integer level;
    }

}
