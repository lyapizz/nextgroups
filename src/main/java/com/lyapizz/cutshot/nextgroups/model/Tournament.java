package com.lyapizz.cutshot.nextgroups.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Tournament {

    int id;
    String name;
    String categories;
    String url;
    String date;

}
