package com.lyapizz.cutshot.nextgroups.model.response;

import java.util.List;

import com.lyapizz.cutshot.nextgroups.model.GroupResult;
import lombok.Value;

@Value
public class GroupResultResponse {

    String tournament;

    List<GroupResult> groupResults;

}
