package com.lyapizz.cutshot.nextgroups.model;

import java.util.List;

import lombok.Value;

@Value
public class GroupResultResponse {

    String tournament;

    List<GroupResult> groupResults;

}
