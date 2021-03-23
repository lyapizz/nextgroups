package com.lyapizz.cutshot.nextgroups.model;

import java.util.List;

import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Value
public class TournamentPlayCards {

    private static final Logger LOG = LoggerFactory.getLogger(TournamentPlayCards.class);

    List<String> teamsPages;

    public boolean containsPlayer(String surname) {
        return teamsPages.stream().anyMatch(s -> s.contains(surname));
    }

    public boolean quotaIsReached(Integer quota) {
        boolean isReached = teamsPages.size() == quota;
        if (!isReached) {
            LOG.info("Quota " + quota + " for this tournament is not fulfilled!");
        }
        return isReached;
    }
}
