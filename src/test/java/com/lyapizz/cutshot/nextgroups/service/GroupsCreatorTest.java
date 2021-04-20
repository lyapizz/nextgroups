package com.lyapizz.cutshot.nextgroups.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.lyapizz.cutshot.nextgroups.model.Group;
import com.lyapizz.cutshot.nextgroups.model.Team;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class GroupsCreatorTest {
    GroupsCreator groupsCreator4Teams = new GroupsCreator(4);
    GroupsCreator groupsCreator6Teams = new GroupsCreator(6);

    @Test
    void create2Groups_8teams() {
        List<Team> teams = createTeams(8);

        List<Group> groups = groupsCreator4Teams.createGroups(teams);
        assertEquals(2, groups.size());
        // check first group
        assertEquals(8, groups.get(0).getTeams().get(0).getCommonRating());
        assertEquals(4, groups.get(0).getTeams().get(1).getRandomSeed());
        assertEquals(3, groups.get(0).getTeams().get(2).getRandomSeed());
        assertEquals(5, groups.get(0).getTeams().get(3).getCommonRating());
    }

    @Test
    void create2Groups_12teams() {
        List<Team> teams = createTeams(12);

        List<Group> groups = groupsCreator6Teams.createGroups(teams);
        assertEquals(2, groups.size());
        // check first group
        assertEquals(12, groups.get(0).getTeams().get(0).getCommonRating());
        assertEquals(8, groups.get(0).getTeams().get(1).getRandomSeed());
        assertEquals(7, groups.get(0).getTeams().get(2).getRandomSeed());
        assertEquals(6, groups.get(0).getTeams().get(3).getRandomSeed());
        assertEquals(5, groups.get(0).getTeams().get(4).getRandomSeed());
        assertEquals(9, groups.get(0).getTeams().get(5).getCommonRating());

    }

    @Test
    void create3Groups_12teams() {
        List<Team> teams = createTeams(12);

        List<Group> groups = groupsCreator4Teams.createGroups(teams);
        assertEquals(3, groups.size());
        // check first group
        assertEquals(12, groups.get(0).getTeams().get(0).getCommonRating());
        assertEquals(6, groups.get(0).getTeams().get(1).getRandomSeed());
        assertEquals(5, groups.get(0).getTeams().get(2).getRandomSeed());
        assertEquals(7, groups.get(0).getTeams().get(3).getCommonRating());

    }

    @Test
    void create4Groups_16teams() {
        List<Team> teams = createTeams(16);

        List<Group> groups = groupsCreator4Teams.createGroups(teams);
        assertEquals(4, groups.size());
        // check first group
        assertEquals(16, groups.get(0).getTeams().get(0).getCommonRating());
        assertEquals(8, groups.get(0).getTeams().get(1).getRandomSeed());
        assertEquals(7, groups.get(0).getTeams().get(2).getRandomSeed());
        assertEquals(9, groups.get(0).getTeams().get(3).getCommonRating());

    }

    @Test
    @Disabled("not possible, because quota is not reached")
    void create4Groups_13teams_notEnoughTeams() {
        List<Team> teams = createTeams(13);

        List<Group> groups = groupsCreator4Teams.createGroups(teams);
        assertEquals(4, groups.size());
        // check first group
        assertEquals(16, groups.get(0).getTeams().get(0).getCommonRating());
        assertEquals(8, groups.get(0).getTeams().get(1).getRandomSeed());
        assertEquals(7, groups.get(0).getTeams().get(2).getRandomSeed());
        assertEquals(9, groups.get(0).getTeams().get(3).getCommonRating());

    }

    @Test
    void create6Groups_24teams() {
        List<Team> teams = createTeams(24);

        List<Group> groups = groupsCreator4Teams.createGroups(teams);
        assertEquals(6, groups.size());
        // check first group
        assertEquals(24, groups.get(0).getTeams().get(0).getCommonRating());
        assertEquals(12, groups.get(0).getTeams().get(1).getRandomSeed());
        assertEquals(11, groups.get(0).getTeams().get(2).getRandomSeed());
        assertEquals(13, groups.get(0).getTeams().get(3).getCommonRating());

        for(Group group: groups){
            System.out.println(group.toString());
        }
    }

    private List<Team> createTeams(int size) {
        List<Team> result = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            result.add(new Team("TEAM" + i + "_1", "TEAM" + i +"_2", i, i, "url"));
        }
        return result;
    }
}