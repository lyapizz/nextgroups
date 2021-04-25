package com.lyapizz.cutshot.nextgroups.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.lyapizz.cutshot.nextgroups.model.Format;
import com.lyapizz.cutshot.nextgroups.model.Group;
import com.lyapizz.cutshot.nextgroups.model.Team;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GroupsCreator {

    public List<Group> createGroups(List<Team> teams, Format format) {
        Format nonNullFormat = format != null ? format : Format.W_SINGLE_4;

        int numberOfGroups = (int) Math.ceil(1.0 * teams.size() / nonNullFormat.getTeamsInGroup());

        //1: sort by rating
        teams.sort(Comparator.comparingInt(team -> -team.getCommonRating()));
        //2: find half with maximum rating
        List<Team> topTeams = teams.stream()
                .limit(numberOfGroups * 2)
                .collect(Collectors.toList());
        //3: add them to groups
        List<Group> groups = initGroups(topTeams);
        //4: fill groups by random groups by seed
        List<Team> loserTeams = teams.stream()
                .skip(numberOfGroups * 2)
                .sorted(Comparator.comparingInt(team -> -team.getRandomSeed()))
                .collect(Collectors.toList());
        return extendByRandomTeams(groups, loserTeams);
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

    private List<Group> extendByRandomTeams(List<Group> groups, List<Team> loserTeams) {
        int numberToAdd = loserTeams.size()/groups.size();
        int curNumberToAdd = numberToAdd;
        int curGroupIndex = 0;
        for(Team loserTeam : loserTeams){
            if(curNumberToAdd == 0){
                curGroupIndex++;
                curNumberToAdd = numberToAdd;
            }
            List<Team> curGroupTeams = groups.get(curGroupIndex).getTeams();
            curGroupTeams.add(curGroupTeams.size() - 1, loserTeam);
            curNumberToAdd--;
        }
        return groups;
    }

}
