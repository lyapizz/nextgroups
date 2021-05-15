package com.lyapizz.cutshot.nextgroups.model.response;

import java.util.List;

import com.lyapizz.cutshot.nextgroups.model.Tournament;
import lombok.Value;

@Value
public class CalenderResponse {

    List<Tournament> tournaments;

}
