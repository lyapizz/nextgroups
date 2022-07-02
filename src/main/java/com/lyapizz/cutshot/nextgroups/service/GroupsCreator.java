package com.lyapizz.cutshot.nextgroups.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.lyapizz.cutshot.nextgroups.model.Format;
import com.lyapizz.cutshot.nextgroups.model.Group;
import com.lyapizz.cutshot.nextgroups.model.Team;
import org.springframework.stereotype.Component;

@Component
public class GroupsCreator {

    public List<Group> createGroups(List<Team> teams, Format format) {
        Format nonNullFormat = format != null ? format : Format.W_SINGLE_4;
        if(nonNullFormat == Format.W_SINGLE_6){
            throw new IllegalStateException("Format W_SINGLE_6 is not supported yet!");
        }

        int numberOfGroups = (int) Math.ceil(1.0 * teams.size() / nonNullFormat.getTeamsInGroup());

        //1: sort by rating then by level then by random seed
        Comparator<Team> ratingComparator = Comparator.comparingInt(team -> -team.getCommonRating());
        Comparator<Team> levelComparator = Comparator.comparingInt(team -> -team.getLevel());
        Comparator<Team> randomSeedComparator = Comparator.comparingInt(team -> -team.getRandomSeed());

        teams.sort(levelComparator
                .thenComparing(ratingComparator)
                .thenComparing(randomSeedComparator));
        //2: find half with maximum rating
        List<Team> topTeams = teams.stream()
                .limit(numberOfGroups * 2)
                .collect(Collectors.toList());
        //3: add them to groups
        List<Group> groups = initGroups(topTeams);
        //4: fill groups by random groups by seed
        List<Team> losersTeams = teams.stream()
                .skip(numberOfGroups * 2)
                .collect(Collectors.toList());
        return extendByOtherTeams(groups, losersTeams);
    }

    private List<Group> initGroups(List<Team> topTeams) {
        List<Group> result = new ArrayList<>();
        int i = 0;
        int j = topTeams.size() - 1;
        while (i < j) {
            List<Team> teams = new ArrayList<>();
            teams.add(topTeams.get(i));
            teams.add(topTeams.get(j));
            result.add(new Group(i + 1, teams));
            i++;
            j--;
        }
        return result;
    }

    private List<Group> extendByOtherTeams(List<Group> groups, List<Team> losersTeams) {
        int i = 0;
        int j = losersTeams.size() - 1;
        int curGroupIndex = 0;
        while (i < j) {
            Group curGroup = groups.get(curGroupIndex);
            curGroup.getTeams().add(losersTeams.get(i));
            curGroup.getTeams().add(losersTeams.get(j));
            i++;
            j--;
            curGroupIndex++;
        }
        return groups;
    }

}
