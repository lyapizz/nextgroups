package com.lyapizz.cutshot.nextgroups.model;

import java.util.List;

import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Value
public class TournamentPlayCards {

    private static final Logger LOG = LoggerFactory.getLogger(TournamentPlayCards.class);

    List<TeamPage> teamsPages;

    public boolean containsPlayer(String surname) {
        return teamsPages.stream()
                .map(teamPage -> teamPage.url)
                .anyMatch(s -> s.contains(surname));
    }

    public boolean quotaIsReached(Integer quota) {
        boolean isReached = teamsPages.size() == quota;
        if (!isReached) {
            LOG.info("Quota " + quota + " for this tournament is not fulfilled!");
        }
        return isReached;
    }

    @Value
    public static class TeamPage {
        String url;
        Integer commonRating;
        Integer level;
    }

}
